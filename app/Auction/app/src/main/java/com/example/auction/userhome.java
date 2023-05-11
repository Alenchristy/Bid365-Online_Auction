package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
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

public class userhome extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {


    ListView ls;
    GridView gw;
    String url,url1;
    SearchView sv1;
    ArrayList<String>pro,img,price,pid,cid,cimg,cname,status,loc;
    SharedPreferences sh;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        ls = findViewById(R.id.lv1);
        gw = findViewById(R.id.gv1);
        sv1=findViewById(R.id.sv);
        sv1.setOnQueryTextListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(userhome.this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        url1 ="http://"+sh.getString("ip", "") + ":5000/search_view";

        url ="http://"+sh.getString("ip", "") + ":5000/select_home";
        RequestQueue queue = Volley.newRequestQueue(userhome.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject result=new JSONObject(response);
                    JSONArray ar=new JSONArray(result.getString("pro"));
                    JSONArray cr=new JSONArray(result.getString("cat"));

                    pro= new ArrayList<>();
                    price= new ArrayList<>();
                    img=new ArrayList<>();
                    pid=new ArrayList<>();
                    cid= new ArrayList<>();
                    cimg=new ArrayList<>();
                    cname=new ArrayList<>();
                    status=new ArrayList<>();
                    loc=new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        pro.add(jo.getString("pname"));
                        price.add(jo.getString("price"));
                        img.add(jo.getString("img"));
                        loc.add(jo.getString("loc"));
                        pid.add(jo.getString("id"));
                        status.add(jo.getString("status"));



                    }
                    for(int i=0;i<cr.length();i++)
                    {
                        JSONObject jo=cr.getJSONObject(i);

                        cid.add(jo.getString("id"));
                        cname.add(jo.getString("c_name"));
                        cimg.add(jo.getString("img"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    ls.setAdapter(new customcat(userhome.this,cimg,cname,cid));

//                    ls.setDivider(null);
//                    ls.setDividerHeight(0);
//                    ls.setHorizontalScrollBarEnabled(false);

                    gw.setAdapter(new customproduct(userhome.this,img,pro,price,status,loc,pid));
//                    ls.setOnItemClickListener(userhome.this);

                } catch (Exception e) {
//                    Toast.makeText(userhome.this, "err===="+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(userhome.this, "err"+error, Toast.LENGTH_SHORT).show();
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



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.auctions:
//                bottomNavigationView.setSelectedItemId(R.id.auctions);
                Intent i = new Intent(getApplicationContext(),auctions.class);
                startActivity(i);
                return true;

            case R.id.sell:
//                bottomNavigationView.setSelectedItemId(R.id.sell);
                Intent k = new Intent(getApplicationContext(),adproducts.class);
                startActivity(k);
                return true;

            case R.id.bids:
//                bottomNavigationView.setSelectedItemId(R.id.bids);
                Intent m = new Intent(getApplicationContext(),bid.class);
                startActivity(m);
                return true;

            case R.id.profile:
//                bottomNavigationView.setSelectedItemId(R.id.profile);
                Intent u = new Intent(getApplicationContext(),userprofile.class);
                startActivity(u);
                return true;

        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }



    @Override
    public boolean onQueryTextChange(String s) {
        url1 ="http://"+sh.getString("ip", "") + ":5000/search_view";


        RequestQueue queue = Volley.newRequestQueue(userhome.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(userhome.this, ""+response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);


                    pro= new ArrayList<>();
                    price= new ArrayList<>();
                    img=new ArrayList<>();
                    loc=new ArrayList<>();
                    pid=new ArrayList<>();
                    status=new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        pro.add(jo.getString("pname"));
                        price.add(jo.getString("price"));
                        img.add(jo.getString("img"));
                        pid.add(jo.getString("id"));
                        loc.add(jo.getString("loc"));
                        status.add(jo.getString("status"));



                    }


                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    gw.setAdapter(new customproduct(userhome.this,img,pro,price,status,loc,pid));
//                    ls.setOnItemClickListener(userhome.this);

                } catch (Exception e) {
//                    Toast.makeText(userhome.this, "err===="+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(userhome.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid",sh.getString("lid",""));
                params.put("txt",s);

                return params;
            }
        };
        queue.add(stringRequest);

        return false;

    }
    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), MainActivity4.class);
        startActivity(h);
    }
}