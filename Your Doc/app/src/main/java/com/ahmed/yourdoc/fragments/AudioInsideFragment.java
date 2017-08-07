package com.ahmed.yourdoc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
    SeekBar audioInsideSeekBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.audio_inside_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void show(Context ctx) {
        Toast.makeText(ctx, "hey", Toast.LENGTH_LONG).show();
    }
    
}
