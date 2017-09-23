package com.ahmed.yourdoc.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.yourdoc.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView registerNowTV, newUserTV, copyRightTV;
    EditText nameET, passET;
    Button submit;
    RadioGroup radioType;
    int selectendId;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                .getString("logged","").equals("true")){
            startActivity(new Intent(LoginActivity.this,RecordActivity.class));
        }
        init();
    }

    private void init() {
        registerNowTV = (TextView) findViewById(R.id.register);
        newUserTV = (TextView) findViewById(R.id.new_user);
        copyRightTV = (TextView) findViewById(R.id.copy_right);
        nameET = (EditText) findViewById(R.id.name_edittext);
        passET = (EditText) findViewById(R.id.pass_edittext);
        submit= (Button) findViewById(R.id.login_submit);
        radioType= (RadioGroup) findViewById(R.id.radio_type);
        selectendId=radioType.getCheckedRadioButtonId();
        switch (selectendId){
            case R.id.radio_doc:
                type=1;
                break;
            case R.id.radio_patient:
                type=0;
                break;
            default:
                break;
        }
        submit.setOnClickListener(this);
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,FirstLogin.class));

            }
        });

        setFonts();

    }

    private void setFonts() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        registerNowTV.setTypeface(face);
        newUserTV.setTypeface(face);
        copyRightTV.setTypeface(face);
        nameET.setTypeface(face);
        passET.setTypeface(face);
        submit.setTypeface(face);
    }

    @Override
    public void onClick(View view) {
        if (view==submit){
            if(nameET.getText().toString().isEmpty()||passET.getText().toString().isEmpty()){
                Toast.makeText(this,"رجاء إكمال حقول التسجيل ",Toast.LENGTH_LONG).show();
                return;
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            //this is the url where you want to send the request
            //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
            String url = "http://7aga-7elwa.com/BeWell/api/login";

            // Request a string response from the provided URL.


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.

                            try {
                                JSONObject obj=new JSONObject(response);
                                String token=obj.getJSONObject("response").getString("token");
                                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("token",token).apply();
                                String oldtoken=PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                        .getString("token","Nan");
                                Log.d("zzzldToken", oldtoken);
                                Log.d("zzzToken",token);
                                Log.d("zzzReg", PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                        .getString("reg_id",""));
                                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("logged","true").apply();
                                startActivity(new Intent(LoginActivity.this,RecordActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("status11", "onResponse: failed ");
                }
            }) {
                //adding parameters to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", nameET.getText().toString());
                    params.put("password",passET.getText().toString());
                    params.put("registration_id",PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                            .getString("reg_id",""));
                    params.put("account_type","0");
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }
}
