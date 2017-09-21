package com.ahmed.yourdoc.activities;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.value;

public class FirstLogin extends AppCompatActivity implements View.OnClickListener {
    EditText name, pass, confirmPass;
    Button submit;
    TextView alreadyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        init();
    }

    private void init() {
        name = (EditText) findViewById(R.id.name_edittext);
        pass = (EditText) findViewById(R.id.pass_edittext);
        confirmPass = (EditText) findViewById(R.id.confirm_pass);
        alreadyUser = (TextView) findViewById(R.id.first_login_already_user);
        submit = (Button) findViewById(R.id.first_login_submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        RequestQueue queue = Volley.newRequestQueue(FirstLogin.this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        String url = "http://7aga-7elwa.com/BeWell/api/newUser";

        // Request a string response from the provided URL.

        JSONObject object=new JSONObject();
        try {
            object.put("username", name.getText().toString());
            object.put("password",pass.getText().toString());
            object.put("repeat_password",confirmPass.getText().toString());
            object.put("registration_id",PreferenceManager.getDefaultSharedPreferences(FirstLogin.this)
                    .getString("reg_id",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.

                        try {
                            JSONObject obj=new JSONObject(response);
                            String token=obj.getJSONObject("response").getString("token");
                            PreferenceManager.getDefaultSharedPreferences(FirstLogin.this).edit().putString("token",token).apply();
                            Log.d("pToken",token);
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
                params.put("password",pass.getText().toString());
                params.put("repeat_password",confirmPass.getText().toString());
                params.put("registration_id",PreferenceManager.getDefaultSharedPreferences(FirstLogin.this)
                        .getString("reg_id",""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
