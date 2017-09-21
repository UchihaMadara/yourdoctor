package com.ahmed.yourdoc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ahmed.yourdoc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gemo on 8/7/17.
 */

public class AudioInsideFragment extends Fragment {

    View rootView;

    @BindView(R.id.audio_inside_seek_bar)
    SeekBar audioinside;
    Handler handler;
    Runnable runnable;
    MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.audio_inside_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        handler = new Handler();
        audioinside = (SeekBar) getActivity().findViewById(R.id.audio_inside_seek_bar);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.demonstrative);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        audioinside.setMax(mediaPlayer.getDuration());
        audioinside.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
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

    public void lifeCycle() {
        audioinside.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    lifeCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();
        lifeCycle();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        handler.removeCallbacks(runnable);
    }

    public void show(Context ctx) {
        Toast.makeText(ctx, "hey", Toast.LENGTH_LONG).show();
    }

}
