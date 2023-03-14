package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    EditText l,f,a,ph,eml,usr,pas;
    RadioButton r1,r2;
    Button reg;
    String lastname,firstname,adress,gender,phone,email,username,password,url;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        l=findViewById(R.id.ln);
        f=findViewById(R.id.fn);
        a=findViewById(R.id.ads);
        r1=findViewById(R.id.rb1);
        r2=findViewById(R.id.rb2);
        ph=findViewById(R.id.phone);
        eml=findViewById(R.id.email);
        usr=findViewById(R.id.usame);
        pas=findViewById(R.id.pasw);
        reg=findViewById(R.id.regs);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastname=l.getText().toString();
                firstname=f.getText().toString();
                adress=a.getText().toString();
                phone=ph.getText().toString();
                email=eml.getText().toString();
                username=usr.getText().toString();
                password=pas.getText().toString();


                if (r1.isChecked()) {
                    gender = r1.getText().toString();

                } else
                {
                    gender = r2.getText().toString();
                }
                if (firstname.equalsIgnoreCase("")) {
                    f.setError("enter your first name");
                }
                    else if (!firstname.matches("^[a-zA-Z]*$")) {
                    f.setError(" characters  allowed");
                    f.requestFocus();
                }


                else if (lastname.equalsIgnoreCase("")) {
                    l.setError("enter your last name");
                }
                    else if (!lastname.matches("^[a-zA-Z]*$")) {
                    l.setError(" characters  allowed");
                    l.requestFocus();
                }
                else if (gender.equalsIgnoreCase("")) {
                    r1.setError("select gender");
                }
                else if (adress.equalsIgnoreCase("")) {
                    a.setError("enter your address");
                }

                else if (phone.equalsIgnoreCase("")) {
                    ph.setError("enter your phone");
                }
                    else if (phone.length() != 10) {
                    ph.setError(" numbers  allowed");
                }


                else if (email.equalsIgnoreCase("")) {
                    eml.setError("enter your  email");
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    eml.setError("enter valid email");
                    eml.requestFocus();
                }
                    else if (email.matches(("^[a-z]*$"))) {
                    eml.setError(" characters  allowed");
                }
                else if (username.equalsIgnoreCase("")) {
                    usr.setError("enter your  username");
                }
                else  if (password.equalsIgnoreCase("")) {
                    pas.setError("enter your  password");
                }
                else if (!username.matches("^[a-z]*$")) {
                    usr.setError(" characters  allowed");
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/reg";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                Toast.makeText(MainActivity2.this, "" + res, Toast.LENGTH_SHORT).show();

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(MainActivity2.this, "registered successfully ", Toast.LENGTH_SHORT).show();

                                    Intent ik = new Intent(getApplicationContext(), MainActivity4.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(MainActivity2.this, "please try again", Toast.LENGTH_SHORT).show();

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
                            params.put("firstname", firstname);
                            params.put("lastname", lastname);
                            params.put("addr", adress);
                            params.put("gender", gender);
                            params.put("phone", phone);
                            params.put("email", email);
                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
//

            }
        });



    }
}