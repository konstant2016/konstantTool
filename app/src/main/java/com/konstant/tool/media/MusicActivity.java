package com.konstant.tool.media;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.konstant.tool.R;

import java.io.IOException;

public class MusicActivity extends AppCompatActivity {

    private Button mPlay;
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBar;
    private TextView mCurrent, mTotal;
    private CountDownTimer mTimer;
    private boolean isPrepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);
        mPlay = findViewById(R.id.btn_play);
        mSeekBar = findViewById(R.id.btn_seek);
        mCurrent = findViewById(R.id.tv_current);
        mTotal = findViewById(R.id.tv_total);

        try {
            mMediaPlayer = new MediaPlayer();
            AssetFileDescriptor fd = getAssets().openFd("爱在西元前.mp3");
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());

        } catch (IOException e) {
            e.printStackTrace();
            mMediaPlayer.reset();
        }

        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            mMediaPlayer.reset();
            mMediaPlayer.prepareAsync();
            return true;
        });

        mMediaPlayer.setOnPreparedListener(mp -> {
            isPrepared = true;
            int duration = mMediaPlayer.getDuration();
            mTotal.setText(getTimeString(duration));
        });

        mMediaPlayer.prepareAsync();

        if (mMediaPlayer.isPlaying()) {
            mPlay.setText("暂停");
        } else {
            mPlay.setText("播放");
        }

        findViewById(R.id.btn_play).setOnClickListener((v -> {
            if (!isPrepared) return;
            if (mPlay.getText().toString().equals("暂停")) {
                mMediaPlayer.pause();
                ((Button) v).setText("播放");
                subScribePosition(false);
            } else {
                ((Button) v).setText("暂停");
                mMediaPlayer.start();
                subScribePosition(true);
            }
        }));


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

    private void initMediaPlayer() throws Exception {
        MediaPlayer player = new MediaPlayer();
        AssetFileDescriptor fd = getAssets().openFd("爱在西元前.mp3");
        player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });
        player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.release();
        super.onDestroy();
    }
}
