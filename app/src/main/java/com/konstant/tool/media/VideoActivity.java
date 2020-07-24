package com.konstant.tool.media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.konstant.tool.R;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "VideoActivity";

    private Button mBtnPlay;
    private SeekBar mSeekBar;
    private boolean isPrepared = false;
    private CountDownTimer mTimer;
    private MediaPlayer mMediaPlayer;
    private TextView mCurrent, mTotal;
    private SurfaceView mSurfaceView;
    private String url = "http://livewallpaper.playmonetize.com/00ad97d2-f1ac-4187-94bd-052f48743139.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initViews();
        initMediaPlayer();
        initSurfaceView();
        initSeekBar();
    }

    private void initViews() {
        mBtnPlay = findViewById(R.id.btn_play);
        mSeekBar = findViewById(R.id.btn_seek);
        mCurrent = findViewById(R.id.tv_current);
        mTotal = findViewById(R.id.tv_total);
        mSurfaceView = findViewById(R.id.surface_view);
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            mp.reset();
            return true;
        });

        mMediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            Log.d(TAG, "onBufferingUpdate:" + percent);
        });

        mMediaPlayer.setOnPreparedListener(mp -> {
            isPrepared = true;
            int duration = mp.getDuration();
            mTotal.setText(getTimeString(duration));
        });

        mBtnPlay.setOnClickListener((v -> {
            Log.d(TAG, "isPrepared:" + isPrepared);
            if (!isPrepared) return;
            if (mBtnPlay.getText().toString().equals("暂停")) {
                mMediaPlayer.pause();
                ((Button) v).setText("播放");
                subScribePosition(false);
            } else {
                ((Button) v).setText("暂停");
                mMediaPlayer.start();
                subScribePosition(true);
            }
        }));
    }

    private void initSurfaceView() {
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated");
                mMediaPlayer.setDisplay(holder);
                try {
                    mMediaPlayer.setDataSource(url);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed");
            }
        });
    }


    private void initSeekBar(){
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) return;
                int duration = mMediaPlayer.getDuration();
                int value = duration * progress / 100;
                mMediaPlayer.seekTo(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String getTimeString(long length) {
        long total = length / 1000;
        long minutes = total / 60;
        long seconds = total % 60;
        String s1 = String.valueOf(minutes);
        String s2 = String.valueOf(seconds);
        if (minutes < 10) {
            s1 = "0" + s1;
        }
        if (seconds < 10) {
            s2 = "0" + s2;
        }
        return s1 + ":" + s2;
    }

    /**
     * 监听播放进度
     *
     * @param subscribe：true：监听，false：取消监听
     */
    private void subScribePosition(boolean subscribe) {
        if (!subscribe && mTimer != null) {
            mTimer.cancel();
            return;
        }
        int duration = mMediaPlayer.getDuration();
        mTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int position = mMediaPlayer.getCurrentPosition();
                String time = getTimeString(position);
                mCurrent.setText(time);
                int value = position * 100 / duration;
                mSeekBar.setProgress(value);
            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.release();
        super.onDestroy();
    }
}
