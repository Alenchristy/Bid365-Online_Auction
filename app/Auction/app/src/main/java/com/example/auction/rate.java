package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class rate extends AppCompatActivity {
    Button snd;
    EditText rv;
    SharedPreferences sh;
    String review,rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rv=findViewById(R.id.rv);
        snd=findViewById(R.id.snd);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review=rv.getText().toString();


                RatingBar ratingBar = findViewById(R.id.bar);
//                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                    @Override
//                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                        // Get the new rating value
//                        float newRating = ratingBar.getRating();
//                        // Do something with the new rating value
//                    }
//                });


                    RequestQueue queue = Volley.newRequestQueue(rate.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/ratng";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                Toast.makeText(rate.this, "" + res, Toast.LENGTH_SHORT).show();

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(rate.this, "registered successfully ", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), rate.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(rate.this, "please try again", Toast.LENGTH_SHORT).show();

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
                            params.put("review", review);
                            params.put("bd",getIntent().getStringExtra("bid"));
                            params.put("rating", String.valueOf(ratingBar.getRating()));

                            return params;
                        }
                    };
                    queue.add(stringRequest);
            }
        });
    }
}