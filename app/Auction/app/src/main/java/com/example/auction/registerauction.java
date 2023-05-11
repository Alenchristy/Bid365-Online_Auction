package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class registerauction extends AppCompatActivity {
    Button regauc,reg1;
    SharedPreferences sh;
    String url1;
    CheckBox ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerauction);
        regauc = findViewById(R.id.regauc);
        ch = findViewById(R.id.ch);
        reg1 = findViewById(R.id.regauc1);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String res=sh.getString("res","");
        if (res.equalsIgnoreCase("1")) {
//            reg1.setEnabled(false);
            reg1.setVisibility(View.GONE);

            regauc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ch.isChecked()) {
//                        Toast.makeText(registerauction.this, "Accepted", Toast.LENGTH_SHORT).show();
                        RequestQueue queue = Volley.newRequestQueue(registerauction.this);
                        url1 = "http://" + sh.getString("ip", "") + ":5000/regauction";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String res = json.getString("result");
                                    Toast.makeText(registerauction.this, "" + res, Toast.LENGTH_SHORT).show();

                                    if (res.equalsIgnoreCase("success")) {
                                        Toast.makeText(registerauction.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                                        Intent ik = new Intent(getApplicationContext(), userhome.class);
                                        ik.putExtra("pid",getIntent().getStringExtra("pid"));
                                        startActivity(ik);



                                    } else {

                                        Toast.makeText(registerauction.this, "please try again", Toast.LENGTH_SHORT).show();

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
                                params.put("lid", sh.getString("lid", ""));
                                params.put("pid", getIntent().getStringExtra("pid"));
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    } else {
                        Toast.makeText(registerauction.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }
        else {
            regauc.setVisibility(View.GONE);
            reg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(registerauction.this, "Already registerd", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
