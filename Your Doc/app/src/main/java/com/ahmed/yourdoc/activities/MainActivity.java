package com.ahmed.yourdoc.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.ahmed.yourdoc.fragments.TitleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickChild {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    public static MainActivity mInstance;
    String names[] = Constant.name;
    String subNames[] = Constant.subName;
    ArrayList<String[]>sub=Constant.getSub();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.frame)
    FrameLayout frame;

    TitleFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mInstance=this;
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

        //setFragment();
    }

    public void setFragment() {
        fragment = new TitleFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "TitleFragment").commit();
    }

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "TitleFragment").commit();
    }

    private List<TitleMenu> getList() {
        List<TitleMenu> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String [] current=sub.get(i);
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
        if (name.equalsIgnoreCase("google")){
//            AudioInsideFragment fragment=new AudioInsideFragment();
//            MainActivity.mInstance.replaceFragment(fragment);
//            fragment.show(this);
            startActivity(new Intent(this,AudioInside.class));
        }
        drawerLayout.closeDrawers();
    }
}
