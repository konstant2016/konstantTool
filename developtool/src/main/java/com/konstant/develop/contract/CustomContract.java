package com.konstant.develop.contract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 时间：2023/5/28 19:29
 * 作者：吕卡
 * 需求：打开分支视频，等待用户播放完成后，原视频跳转到指定位置
 */

public class CustomContract extends ActivityResultContract<String, Long> {

    /**
     * fileName：就是外部传入过来的文件名字
     */
    public Intent createIntent(@NonNull Context context, String videoId) {
        Intent intent = new Intent(context, BranchActivity.class);
        intent.putExtra("videoId", videoId);
        return intent;
    }

    @Override
    public Long parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getLongExtra("timeStamp", -1L);
        }
        return -1L;
    }
}
