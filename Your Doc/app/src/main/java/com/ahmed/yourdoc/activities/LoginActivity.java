package com.ahmed.yourdoc.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmed.yourdoc.R;

public class LoginActivity extends AppCompatActivity {
    TextView registerNowTV, newUserTV, copyRightTV;
    EditText nameET, passET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        registerNowTV = (TextView) findViewById(R.id.register);
        newUserTV = (TextView) findViewById(R.id.new_user);
        copyRightTV = (TextView) findViewById(R.id.copy_right);
        nameET = (EditText) findViewById(R.id.name_edittext);
        passET = (EditText) findViewById(R.id.pass_edittext);
        setFonts();
    }

    private void setFonts() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        registerNowTV.setTypeface(face);
        newUserTV.setTypeface(face);
        copyRightTV.setTypeface(face);
        nameET.setTypeface(face);
        passET.setTypeface(face);
    }
}
