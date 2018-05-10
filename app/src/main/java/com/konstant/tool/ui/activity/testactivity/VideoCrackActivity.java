package com.konstant.tool.ui.activity.testactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.konstant.tool.R;
import com.konstant.tool.base.BaseActivity;

public class VideoCrackActivity extends BaseActivity {

    private EditText mEditText;
    private Button mButton;
    private Spinner mSpinner;

    private String baseUrl = "https://api.47ks.com/webcloud/?v=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_crack);
        setTitle("VIP视频解析");
        initBaseViews();
        initSpinner();
        initButton();
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initBaseViews() {
        mEditText = (EditText) findViewById(R.id.edit_text);
        mButton = (Button) findViewById(R.id.btn_creak);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        findViewById(R.id.img_back).setOnClickListener((v) -> finish());
    }

    private void initSpinner() {
        String[] items = getResources().getStringArray(R.array.source_name);
        final String[] links = getResources().getStringArray(R.array.source_link);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_bg, items){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View root = LayoutInflater.from(VideoCrackActivity.this).inflate(R.layout.item_spinner_pull_down_bg, parent, false);
                TextView tv =  (TextView) root.findViewById(R.id.text_label);
                tv.setText(items[position]);
                return root;
            }
        };

        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                baseUrl = links[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initButton() {
        mButton.setOnClickListener(
            view -> {
                if ((mEditText.getText() == null) || TextUtils.isEmpty(mEditText.getText())) {
                    Toast.makeText(VideoCrackActivity.this, "记得输入地址哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse(baseUrl + mEditText.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        );
    }
}
