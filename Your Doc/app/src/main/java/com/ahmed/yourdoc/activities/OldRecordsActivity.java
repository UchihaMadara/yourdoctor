package com.ahmed.yourdoc.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ahmed.yourdoc.R;
import com.ahmed.yourdoc.adapter.OldRecordsAdapter;

public class OldRecordsActivity extends AppCompatActivity {
    RecyclerView previousRecords;
    OldRecordsAdapter oldRecordsAdapter;
    TextView toolbarUsername , toolbarLogout;
    TextView previousTv,copyright;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_records);
        toolbar= (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbarUsername= (TextView) toolbar.findViewById(R.id.user_name);
        toolbarLogout= (TextView) toolbar.findViewById(R.id.logout);
        previousTv= (TextView) findViewById(R.id.tv_previous);
        copyright= (TextView) findViewById(R.id.copy_right);
        setTypeFace();

        previousRecords= (RecyclerView) findViewById(R.id.old_recycler_view);
        oldRecordsAdapter=new OldRecordsAdapter(this);
        previousRecords.setLayoutManager(new LinearLayoutManager(this));
        previousRecords.setAdapter(oldRecordsAdapter);
    }

    private void setTypeFace() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        toolbarLogout.setTypeface(face);
        toolbarUsername.setTypeface(face);
        previousTv.setTypeface(face);
        copyright.setTypeface(face);
    }
}
