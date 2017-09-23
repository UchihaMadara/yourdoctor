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

public class FirstLogin extends AppCompatActivity implements View.OnClickListener {
    EditText name, pass, confirmPass;
    Button submit;
    TextView alreadyUser;
    private TextView copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        Log.d("ttt", PreferenceManager.getDefaultSharedPreferences(FirstLogin.this)
                .getString("token", ""));
        init();
    }

    private void init() {
        name = (EditText) findViewById(R.id.name_edittext);
        pass = (EditText) findViewById(R.id.pass_edittext);
        confirmPass = (EditText) findViewById(R.id.confirm_pass);
        alreadyUser = (TextView) findViewById(R.id.first_login_already_user);
        submit = (Button) findViewById(R.id.first_login_submit);
        submit.setOnClickListener(this);
        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstLogin.this, LoginActivity.class));
            }
        });
        copy = (TextView) findViewById(R.id.copy_right);
        setFonts();
    }

    private void setFonts() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        name.setTypeface(face);
        pass.setTypeface(face);
        confirmPass.setTypeface(face);
        alreadyUser.setTypeface(face);
        submit.setTypeface(face);
        copy.setTypeface(face);

    }

    @Override
    public void onClick(View view) {
        if (view == submit) {

            if (name.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || confirmPass.getText().toString().isEmpty()) {
                Toast.makeText(this, "رجاء إكمال حقول التسجيل ", Toast.LENGTH_LONG).show();
                return;
            }
            if (!pass.getText().toString().equals(confirmPass.getText().toString())) {
                Toast.makeText(this, "كلمة المرور غير متطابقة", Toast.LENGTH_LONG).show();
                return;
            }
            RequestQueue queue = Volley.newRequestQueue(FirstLogin.this);
            //this is the url where you want to send the request
            //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
            String url = "http://7aga-7elwa.com/BeWell/api/newUser";

            // Request a string response from the provided URL.

            JSONObject object = new JSONObject();
            try {
                object.put("username", name.getText().toString());
                object.put("password", pass.getText().toString());
                object.put("repeat_password", confirmPass.getText().toString());
                object.put("registration_id", PreferenceManager.getDefaultSharedPreferences(FirstLogin.this)
                        .getString("reg_id", ""));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.

                            try {
                                JSONObject obj = new JSONObject(response);
                                String token = obj.getJSONObject("response").getString("token");
                                PreferenceManager.getDefaultSharedPreferences(FirstLogin.this).edit().putString("token", token).apply();
                                Log.d("zzzpToken", token);
                                PreferenceManager.getDefaultSharedPreferences(FirstLogin.this).edit().putString("logged", "true").apply();
                                startActivity(new Intent(new Intent(FirstLogin.this, RecordActivity.class)));
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
                    params.put("username", name.getText().toString());
                    params.put("password", pass.getText().toString());
                    params.put("repeat_password", confirmPass.getText().toString());
                    params.put("registration_id", PreferenceManager.getDefaultSharedPreferences(FirstLogin.this)
                            .getString("reg_id", ""));
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}
