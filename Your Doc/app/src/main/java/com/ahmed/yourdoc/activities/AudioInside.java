package com.ahmed.yourdoc.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ahmed.yourdoc.Constant;
import com.ahmed.yourdoc.R;
import com.ahmed.yourdoc.adapter.RecyclerAdapter;
import com.ahmed.yourdoc.fragments.AudioInsideFragment;
import com.ahmed.yourdoc.models.SubTitle;
import com.ahmed.yourdoc.models.TitleMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioInside extends AppCompatActivity implements RecyclerAdapter.ItemClickChild {

    public static AudioInside mInstance;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    String names[] = Constant.name;
    String subNames[] = Constant.subName;
    ArrayList<String[]> sub = Constant.getSub();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.frame)
    FrameLayout frame;

    AudioInsideFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_inside);
        ButterKnife.bind(this);
        mInstance = this;
        setSupportActionBar(toolbar);
        final ActionBar actionar = getSupportActionBar();
        actionar.setDisplayHomeAsUpEnabled(true);
        actionar.setHomeAsUpIndicator(R.drawable.ic_menu);

        List<TitleMenu> list = getList();
        RecyclerAdapter adapter = new RecyclerAdapter(this, list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        setFragment();
    }

    public void setFragment() {
        fragment = new AudioInsideFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "MainActivityFragment").commit();
    }

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "MainActivityFragment").commit();
    }

    private List<TitleMenu> getList() {
        List<TitleMenu> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String[] current = sub.get(i);
            List<SubTitle> subTitles = new ArrayList<>();
            for (int j = 0; j < current.length; j++) {
                SubTitle subTitle = new SubTitle(current[j]);
                subTitles.add(subTitle);
            }
            TitleMenu model = new TitleMenu(names[i], subTitles, null);
            list.add(model);
        }
        return list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChildClick(int position) {
        String name = subNames[position];
        if (name.equalsIgnoreCase("google")) {
//            AudioInsideFragment fragment=new AudioInsideFragment();
//            MainActivity.mInstance.replaceFragment(fragment);
//            fragment.show(this);
            // startActivity(new Intent(this,AudioInside.class));
        }
        drawerLayout.closeDrawers();
    }
    /*
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
     */
}
