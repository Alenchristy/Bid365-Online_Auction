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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class all_rating extends AppCompatActivity implements AdapterView.OnItemClickListener {
    BottomNavigationView bottomNavigationView;
    ListView ls;
    String url;
    ArrayList<String>name,rating,review;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rating);
        ls = findViewById(R.id.list1);

//        bottomNavigationView.setSelectedItemId(R.id.s);
//        bottomNavigationView.setOnNavigationItemSelectedListener(sold_product.this);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/allrating";
        RequestQueue queue = Volley.newRequestQueue(all_rating.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(all_rating.this, ""+response, Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);

                    name= new ArrayList<>();
                    review= new ArrayList<>();
                    rating= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        review.add(jo.getString("review"));
                        rating.add(jo.getString("rating"));
                    }

//                     ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    lv.setAdapter(ad);
                    if(ar.length()!=0) {
                        ls.setAdapter(new customrating(all_rating.this, name, review, rating));
                        ls.setOnItemClickListener(all_rating.this);
                    }
                    else
                    {
//                        nr.setText("No data to show");
                        String msg="No Ratings And Reviews";
                        ArrayAdapter<String> ad=new ArrayAdapter<String>(all_rating.this,android.R.layout.simple_list_item_1, Collections.singletonList(msg));
                    ls.setAdapter(ad);
                    }

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(all_rating.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid", getIntent().getStringExtra("pid"));
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
        Intent h = new Intent(getApplicationContext(), productview.class);
        startActivity(h);
    }
}