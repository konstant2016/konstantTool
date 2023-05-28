package com.konstant.develop.contract;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.konstant.develop.R;

/**
 * StartActivityForResult()
 * StartIntentSenderForResult()
 * RequestMultiplePermissions()
 * RequestPermission()
 * TakePicturePreview()
 * TakePicture()
 * TakeVideo()
 * PickContact()
 * GetContent()
 * GetMultipleContents()
 * OpenDocument()
 * OpenMultipleDocuments()
 * OpenDocumentTree()
 * CreateDocument()
 */

public class ContractActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> resultLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {

            }
        }
    });

    ActivityResultLauncher<String> permissionLauncher = this.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            Log.d("ContractActivity", "是否获取了权限：" + result);
        }
    });

    ActivityResultLauncher<Uri> savePictureLauncher = this.registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {

        }
    });

    ActivityResultLauncher<Void> getPictureLauncher = this.registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
        @Override
        public void onActivityResult(Bitmap result) {

        }
    });




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,new DemoFragment())
                        .commitAllowingStateLoss();

    }
}
