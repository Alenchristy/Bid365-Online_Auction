package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
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

public class allauctions extends AppCompatActivity {
    String url;
    SharedPreferences sh;
    ArrayList<String>pro,img,price,pid,status,loc;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allauctions);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        gv=findViewById(R.id.gv);
Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_LONG).show();

//        url ="http://"+sh.getString("ip", "") + ":5000/select_home";
//        RequestQueue queue = Volley.newRequestQueue(allauctions.this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++",response);
//                try {
////                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();
//                    JSONObject result=new JSONObject(response);
//                    JSONArray ar=new JSONArray(result.getString("pro"));
//                    JSONArray cr=new JSONArray(result.getString("cat"));
//
//                    pro= new ArrayList<>();
//                    price= new ArrayList<>();
//                    img=new ArrayList<>();
//                    pid=new ArrayList<>();
//                    status=new ArrayList<>();
//                    loc=new ArrayList<>();
//
//
//                    for(int i=0;i<ar.length();i++)
//                    {
//                        JSONObject jo=ar.getJSONObject(i);
//
//                        pro.add(jo.getString("pname"));
//                        price.add(jo.getString("price"));
//                        img.add(jo.getString("img"));
//                        loc.add(jo.getString("loc"));
//                        pid.add(jo.getString("id"));
//                        status.add(jo.getString("status"));
//
//
//
//                    }
//
//
//                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    //lv.setAdapter(ad);
//
//
//
////                    ls.setDivider(null);
////                    ls.setDividerHeight(0);
////                    ls.setHorizontalScrollBarEnabled(false);
//
//                    gv.setAdapter(new customproduct(allauctions.this,img,pro,price,status,loc,pid));
////                    ls.setOnItemClickListener(userhome.this);
//
//                } catch (Exception e) {
//                    Toast.makeText(allauctions.this, "err===="+e, Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
////                Toast.makeText(userhome.this, "err"+error, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("lid",sh.getString("lid",""));
//
//                return params;
//            }
//        };
//        queue.add(stringRequest);
    }
}