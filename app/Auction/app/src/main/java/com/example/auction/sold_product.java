package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sold_product extends AppCompatActivity implements AdapterView.OnItemClickListener {
    BottomNavigationView bottomNavigationView;
    ListView ls;
    String url;
    ArrayList<String>pro,img,stprice,bprice,pid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_product);
        ls = findViewById(R.id.list1);
//        bottomNavigationView.setSelectedItemId(R.id.s);
//        bottomNavigationView.setOnNavigationItemSelectedListener(sold_product.this);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/soldproduct";
        RequestQueue queue = Volley.newRequestQueue(sold_product.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);

                    pro= new ArrayList<>();
                    stprice= new ArrayList<>();
                    bprice= new ArrayList<>();
                    img=new ArrayList<>();
                    pid=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        pro.add(jo.getString("pname"));
                        stprice.add(jo.getString("sprice"));
                        bprice.add(jo.getString("bprice"));
                        img.add(jo.getString("image"));
                        pid.add(jo.getString("id"));
                    }

//                     ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    lv.setAdapter(ad);

                    ls.setAdapter(new custom_sold(sold_product.this,img,pro,stprice,bprice,pid));
                    ls.setOnItemClickListener(sold_product.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(sold_product.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);




//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),adproduct.class);
//                startActivity(i);
//            }
//        });


    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.home:
//                Intent h = new Intent(getApplicationContext(), userhome.class);
//                startActivity(h);
//                return true;
//
//            case R.id.auctions:
//                Intent i = new Intent(getApplicationContext(), auctions.class);
//                startActivity(i);
//                return true;
//
//            case R.id.bids:
//                Intent m = new Intent(getApplicationContext(), bid.class);
//                startActivity(m);
//                return true;
//
//            case R.id.profile:
//                Intent u = new Intent(getApplicationContext(), userprofile.class);
//                startActivity(u);
//                return true;
//
//        }
//        return false;
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), userhome.class);
        startActivity(h);
    }
}