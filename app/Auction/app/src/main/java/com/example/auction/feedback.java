package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class feedback extends AppCompatActivity {
    Button fed;
    TextView feed;
    SharedPreferences sh;
    String feed1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        fed=findViewById(R.id.fed);
        feed=findViewById(R.id.feed);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        fed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                feed1=feed.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(feedback.this);
                String url = "http://" + sh.getString("ip", "") + ":5000/feedb";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");
                            Toast.makeText(feedback.this, "" + res, Toast.LENGTH_SHORT).show();

                            if (res.equalsIgnoreCase("success")) {
                                Toast.makeText(feedback.this, "Feedback sended successfully ", Toast.LENGTH_SHORT).show();

                                Intent ik = new Intent(getApplicationContext(), userprofile.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(feedback.this, "please try again", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("feed", feed1);
                        params.put("lid", sh.getString("lid", ""));
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
}