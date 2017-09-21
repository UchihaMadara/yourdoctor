package com.ahmed.yourdoc.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.ahmed.yourdoc.R;

public class RecordDialogActivity extends Activity implements OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record_dialog);


    }

    @Override
    public void onClick(View v) {



    }



}