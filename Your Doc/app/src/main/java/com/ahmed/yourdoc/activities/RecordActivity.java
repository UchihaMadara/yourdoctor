package com.ahmed.yourdoc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    TextView copyRight, userName, logout, timeStatus;
    Button Record;

    Toolbar toolbar;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;
    private boolean flag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
        //requestCategories();
        //showDialog();
        setFonts();

    }

    private void init() {
        copyRight = (TextView) findViewById(R.id.copy_right_record);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        Record = (Button) findViewById(R.id.start_record);
        timeStatus = (TextView) findViewById(R.id.tv_time_status);
        userName = (TextView) toolbar.findViewById(R.id.user_name);
        logout = (TextView) toolbar.findViewById(R.id.logout);

        Record.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    public void setFonts() {
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        copyRight.setTypeface(face);
        Record.setTypeface(face);
        timeStatus.setTypeface(face);
        userName.setTypeface(face);
        logout.setTypeface(face);
    }

    public void showDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(RecordActivity.this);
        Typeface face = Typeface.createFromAsset(getAssets(), "font_ar.ttf");
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.record_dialog);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //init variables
        Button save = (Button) dialog.findViewById(R.id.save_bt);
        Button cancel= (Button) dialog.findViewById(R.id.cancel_bt);
        EditText name= (EditText) dialog.findViewById(R.id.name_et);
        Spinner category= (Spinner) dialog.findViewById(R.id.spinner);

        //setType face
        save.setTypeface(face);
        cancel.setTypeface(face);
        name.setTypeface(face);

        //setup click listeners
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(RecordActivity.this, "Ok", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    @Override
    public void onClick(View view) {
        if (view == Record) {
            requestCategories();
//            if (Record.getText().equals("أبدأ التسجيل")){
//                Record.setText("انتهيت");
//
//                MediaRecorderReady();
//                record( Environment.getExternalStorageDirectory().getAbsolutePath());
//            }else if(Record.getText().equals("انتهيت")) {
//                Record.setText("أرسل الملف");
//                timeStatus.setText("أعد التسجيل");
//                try {
//                    stopRecording();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else if(Record.getText().equals("أرسل الملف")){
//
//            }

        } else if (view == logout) {
            logoutRequest();


        }
    }

    private void requestCategories() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        String url = "http://7aga-7elwa.com/BeWell/api/getCategories";

        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("Sres", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                           // String token = obj.getJSONObject("response").getString("token");
                            //PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("token",token).apply();

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

                params.put("token",PreferenceManager.getDefaultSharedPreferences(RecordActivity.this)
                        .getString("token","Nan"));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public void logoutRequest(){
        RequestQueue queue = Volley.newRequestQueue(RecordActivity.this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        String url = "http://7aga-7elwa.com/BeWell/api/logOut";

        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("Sres", response);
                        PreferenceManager.getDefaultSharedPreferences(RecordActivity.this).edit()
                                .putString("logged","false").apply();
                        startActivity(new Intent(RecordActivity.this,LoginActivity.class));

                        try {
                            JSONObject obj = new JSONObject(response);
                            //String token = obj.getJSONObject("response").getString("token");


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
                params.put("token", PreferenceManager.getDefaultSharedPreferences(RecordActivity.this)
                        .getString("token", ""));
                params.put("registration_id", PreferenceManager.getDefaultSharedPreferences(RecordActivity.this)
                        .getString("reg_id", ""));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    //for uploading file
    private void doFileUpload() {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mypic.png";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = "http://mywebsite.com/directory/upload.php";

        try {

            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(existingFileName));
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + existingFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }

        //------------------ read the SERVER RESPONSE
        try {

            inStream = new DataInputStream(conn.getInputStream());
            String str;

            while ((str = inStream.readLine()) != null) {

                Log.e("Debug", "Server Response " + str);

            }

            inStream.close();

        } catch (IOException ioex) {
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
    }

    //Recording audio method

    public void record(String path) {
        if (checkPermission()) {
        flag=true;
            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "AudioRecording.mp3";

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Toast.makeText(RecordActivity.this, "Recording started",
                    Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
    }

    //stop recording method
    public void stopRecording() throws IOException{
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    //checking permission for recording
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RecordActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }


}
