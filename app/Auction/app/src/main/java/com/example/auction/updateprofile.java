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
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class updateprofile extends AppCompatActivity {
    EditText fnm, lnm, ads, eml, ph;
    RadioButton r1, r2;
    Button b1;
    String fname, lname, gender,email, adres, phone, url, url1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fnm = findViewById(R.id.fn);
        lnm = findViewById(R.id.ln);
        ads = findViewById(R.id.ads);
        r1 = findViewById(R.id.rb1);
        r2 = findViewById(R.id.rb2);
        eml = findViewById(R.id.email);
        ph = findViewById(R.id.phone);
        b1 = findViewById(R.id.regs);


        url1 = "http://" + sh.getString("ip", "") + ":5000/view_profile";
        RequestQueue queue = Volley.newRequestQueue(updateprofile.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);

                    {
                        JSONObject jo=ar.getJSONObject(0);
                        fnm.setText(jo.getString("fname"));
                        lnm.setText(jo.getString("lname"));
                        eml.setText(jo.getString("email"));
                        ads.setText(jo.getString("adrs"));
                        ph.setText(jo.getString("phon"));
                        String gender=jo.getString("gender");
                        if(gender.equals("Male"))
                        {
                            r1.setChecked(true);
                        }
                        else
                        {
                            r2.setChecked(true);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = lnm.getText().toString();
                lname = fnm.getText().toString();
                email = eml.getText().toString();
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else {
                    gender = r2.getText().toString();
                }
                adres = ads.getText().toString();
                phone = ph.getText().toString();
                if (fname.equalsIgnoreCase("")) {
                    fnm.setError("Enter first Name");
                } else if (!fname.matches("^[a-zA-Z]*$")) {
                    fnm.setError("Characters Allowed");
                    fnm.requestFocus();
                } else if (lname.equalsIgnoreCase("")) {
                    lnm.setError("Enter Second Name");
                } else if (!lname.matches("^[a-zA-Z]*$")) {
                    lnm.setError("Characters Allowed");
                    lnm.requestFocus();
                }
                else if (email.equalsIgnoreCase("")) {
                    eml.setError("Enter E-mail");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    eml.setError("Enter Valid E-mail");
                    eml.requestFocus();
                }
                else if (adres.equalsIgnoreCase("")) {
                    ads.setError("Enter Place");
                }
                else if (phone.equalsIgnoreCase("")) {
                    ph.setError("Enter Phone Number");
                }
                else if (phone.length() != 10) {
                    ph.setError(" numbers  allowed");
                }

                else
                {
                    RequestQueue queue = Volley.newRequestQueue(updateprofile.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/updateprofile";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(updateprofile.this, " updated successfully", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), userprofile.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(updateprofile.this, "failed", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(updateprofile.this, "or " + e, Toast.LENGTH_SHORT).show();

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
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("gender", gender);
                            params.put("email", email);
                            params.put("adres", adres);
                            params.put("phone", phone);
                            params.put("lid", sh.getString("lid", ""));
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }

            }
        });



    }
}