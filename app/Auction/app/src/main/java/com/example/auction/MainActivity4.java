package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity4 extends AppCompatActivity {
    EditText un,ps;
    Button b,reg, ab;
    String username,password,url;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        un=findViewById(R.id.username);
        ps=findViewById(R.id.pswd);
        b=findViewById(R.id.button);
        reg=findViewById(R.id.registernow);
        ab=findViewById(R.id.button3);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), aboutus.class);
                startActivity(in);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=un.getText().toString();
                password=ps.getText().toString();
                if (username.equalsIgnoreCase("")) {
                    un.setError("enter your username");
                    un.requestFocus();
                }
                else if (password.equalsIgnoreCase("")) {
                    ps.setError("enter your password");
                    ps.requestFocus();
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/android_login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("result");

                                if (res.equalsIgnoreCase("valid")) {
                                    String lid = json.getString("id");

                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();


                                    //notifications

                                    Intent is = new Intent(getApplicationContext(),LocationServiceno.class);
                                    startService(is);


                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(MainActivity4.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity4.this, "==" + e, Toast.LENGTH_SHORT).show();

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
                            params.put("username", username);
                            params.put("password", password);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(ik);

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), MainActivity4.class);
        startActivity(h);
    }
}