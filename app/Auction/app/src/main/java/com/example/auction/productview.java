package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class productview extends AppCompatActivity {
    TextView pname,pprice,pdetails,pplace,adate,atime;
    ImageView img;
    String url1,pimg,url2;
    SharedPreferences sh;
    Button dout,regs;
    RatingBar rtb;
    ArrayList pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);
        pname=findViewById(R.id.pname);
        pprice=findViewById(R.id.pprice);
        img=findViewById(R.id.img);
        pdetails=findViewById(R.id.rw);
        pplace=findViewById(R.id.pplace);
        adate=findViewById(R.id.adate);
        atime=findViewById(R.id.atime);
        dout = findViewById(R.id.doubt);
        regs = findViewById(R.id.regs);
        rtb=findViewById(R.id.rtb);
        rtb.setEnabled(false);
        CardView cd=findViewById(R.id.cd);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), all_rating.class);
                in.putExtra("pid",sh.getString("pip",""));
                SharedPreferences.Editor ed=sh.edit();
                ed.commit();
                startActivity(in);
            }
        });


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());






        url1 = "http://" + sh.getString("ip", "") + ":5000/productview";
        RequestQueue queue = Volley.newRequestQueue(productview.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++",response);
                try {
                    JSONArray ar=new JSONArray(response);
                    {
                        JSONObject jo=ar.getJSONObject(0);
                        pname.setText(jo.getString("pname"));
                        pprice.setText(jo.getString("price"));
                        pdetails.setText(jo.getString("details"));
                        pplace.setText(jo.getString("place"));
                        adate.setText(jo.getString("date"));
                        atime.setText(jo.getString("time"));
                        pimg=jo.getString("image");
                        try {
                            rtb.setRating(Float.parseFloat(jo.getString("rating")));
                        }
                        catch (Exception e){
                            rtb.setRating(0);

                        }


                    }
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy =
                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }


                    java.net.URL thumb_u;
                    try {

                        //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

                        thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/media/"+pimg);
                        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                        img.setImageDrawable(thumb_d);

                    }
                    catch (Exception e)
                    {

                        Log.d("errsssssssssssss",""+e);
                    }


                } catch (JSONException e) {
//                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("lid", sh.getString("lid", ""));
                params.put("pid",sh.getString("pip",""));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);








        dout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), Test.class);
                in.putExtra("pid",sh.getString("pip",""));
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("uid","0");
                ed.commit();
                startActivity(in);
            }
        });




        regs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(productview.this);
                url2 = "http://" + sh.getString("ip", "") + ":5000/regauction1";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("result");


                            if (res.equalsIgnoreCase("success")) {
//                                Toast.makeText(productview.this, "registered successfully ", Toast.LENGTH_SHORT).show();
                                Intent is = new Intent(getApplicationContext(), registerauction.class);
                                is.putExtra("pid",sh.getString("pip",""));
                                SharedPreferences.Editor ed=sh.edit();
                                ed.putString("uid","0");
                                ed.putString("res","1");
                                ed.commit();
                                startActivity(is);
                            } else {
//                                Toast.makeText(productview.this, "please try again", Toast.LENGTH_SHORT).show();
                                Intent is = new Intent(getApplicationContext(), registerauction.class);
                                is.putExtra("pid",sh.getString("pip",""));
                                SharedPreferences.Editor ed=sh.edit();
                                ed.putString("uid","0");
                                ed.putString("res","0");
                                ed.commit();
                                startActivity(is);
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
                        params.put("pid", sh.getString("pip",""));
                        return params;
                    }
                };
                queue.add(stringRequest);
            }




        });

//        @Override
//        public void onBackPressed(;) {
//            Intent h = new Intent(getApplicationContext(), MainActivity4.class);
//            startActivity(h);
//        }
    }
    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), userhome.class);
        startActivity(h);
    }
}