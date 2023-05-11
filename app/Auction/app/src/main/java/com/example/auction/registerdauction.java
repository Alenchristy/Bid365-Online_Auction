package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class registerdauction extends AppCompatActivity {
    ListView ls;
    String url;
    ArrayList<String> pro,img,price,date,time;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdauction);
//Toast.makeText(getApplicationContext(),"======Started",Toast.LENGTH_LONG).show();
//        ls = findViewById(R.id.lv1);
//        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        url ="http://"+sh.getString("ip", "") + ":5000/regauction2";
//        RequestQueue queue = Volley.newRequestQueue(registerdauction.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++",response);
//                try {
////                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();
//
//                    JSONArray ar=new JSONArray(response);
//
//                    pro= new ArrayList<>();
//                    price= new ArrayList<>();
//                    img=new ArrayList<>();
//                    date=new ArrayList<>();
//                    time=new ArrayList<>();
//
//
//                    for(int i=0;i<ar.length();i++)
//                    {
//                        JSONObject jo=ar.getJSONObject(i);
//
//                        pro.add(jo.getString("pname"));
//                        price.add(jo.getString("price"));
//                        img.add(jo.getString("image"));
//                        date.add(jo.getString("date"));
//                        time.add(jo.getString("time"));
//
//
//
//
//                    }
//
//                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    //lv.setAdapter(ad);
//
//                    ls.setAdapter(new custom_auction(registerdauction.this,img,pro,price,date,time));
////                    ls.setOnItemClickListener(registerdauction.this);
//
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(),"+++"+e,Toast.LENGTH_LONG).show();
//                    Log.d("=========", e.toString());
//                }
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(registerdauction.this, "err"+error, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("lid",sh.getString("lid",""));
//                return params;
//            }
//        };
//        queue.add(stringRequest);


    }
}