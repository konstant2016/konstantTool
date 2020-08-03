package com.konstant.tool.ui.activity.viewpager2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.konstant.tool.R;

public class ViewPager2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_view_pager2);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle()));

        findViewById(R.id.btn).setOnClickListener(v -> {
            if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            } else {
                viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
