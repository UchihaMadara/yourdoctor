package com.ahmed.yourdoc.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.ahmed.yourdoc.R;

public class AudioInside extends AppCompatActivity {
    SeekBar audioinside;
    Handler handler;
    Runnable runnable;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_inside);
        handler=new Handler();
        audioinside= (SeekBar) findViewById(R.id.audio_inside);
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.demonstrative);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        audioinside.setMax(mediaPlayer.getDuration());
        audioinside.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public void lifeCycle(){
        audioinside.setProgress(mediaPlayer.getCurrentPosition());
        if(mediaPlayer.isPlaying()){
            runnable=new Runnable() {
                @Override
                public void run() {
                    lifeCycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
        lifeCycle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        handler.removeCallbacks(runnable);
    }
}
