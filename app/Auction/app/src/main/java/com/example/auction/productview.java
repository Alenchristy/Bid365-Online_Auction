package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class productview extends AppCompatActivity {
    TextView pname,pprice,pdetails,pplace,adate,atime;
    ImageView img;
    String url1,pimg;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);
        pname=findViewById(R.id.pname);
        pprice=findViewById(R.id.pprice);
        img=findViewById(R.id.img);
        pdetails=findViewById(R.id.pdetails);
        pplace=findViewById(R.id.pplace);
        adate=findViewById(R.id.adate);
        atime=findViewById(R.id.atime);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url1 = "http://" + sh.getString("ip", "") + ":5000/productview";
        RequestQueue queue = Volley.newRequestQueue(productview.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
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
                params.put("pid",getIntent().getStringExtra("pid"));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}