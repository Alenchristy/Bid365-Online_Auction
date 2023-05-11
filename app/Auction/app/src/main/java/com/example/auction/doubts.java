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

public class doubts extends AppCompatActivity implements AdapterView.OnItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    Button b;
    ListView ls;
    String url;
    ArrayList<String> name,uid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts);
//        b = findViewById(R.id.btn);
        ls = findViewById(R.id.list);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.sell);
        bottomNavigationView.setOnNavigationItemSelectedListener(doubts.this);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("pid",getIntent().getStringExtra("pid"));
        ed.commit();
        url = "http://" + sh.getString("ip", "") + ":5000/viewchats";
        RequestQueue queue = Volley.newRequestQueue(doubts.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();

                    JSONArray ar = new JSONArray(response);

                    name = new ArrayList<>();
                    uid=new ArrayList<>();




                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);

                        name.add(jo.getString("pname"));
                        uid.add(jo.getString("uid"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    ls.setAdapter(new customdoubts(doubts.this, name,uid));
                    ls.setOnItemClickListener(doubts.this);

                } catch (Exception e) {
                    Toast.makeText(doubts.this, "++"+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(doubts.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pid",getIntent().getStringExtra("pid"));

                params.put("lid", sh.getString("lid", ""));

                return params;
            }
        };
        queue.add(stringRequest);





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent h = new Intent(getApplicationContext(), userhome.class);
                startActivity(h);
                return true;

            case R.id.auctions:
                Intent i = new Intent(getApplicationContext(), auctions.class);
                startActivity(i);
                return true;

            case R.id.bids:
                Intent m = new Intent(getApplicationContext(), bid.class);
                startActivity(m);
                return true;

            case R.id.profile:
                Intent u = new Intent(getApplicationContext(), userprofile.class);
                startActivity(u);
                return true;

        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
SharedPreferences.Editor ed=sh.edit();
        ed.putString("uid",uid.get(i));
        ed.commit();
Intent ik=new Intent(getApplicationContext(),Test.class);
ik.putExtra("pid",getIntent().getStringExtra("pid"));
startActivity(ik);
    }

//    @Override
//    public void onBackPressed() {
//        Intent h = new Intent(getApplicationContext(), userhome.class);
//        startActivity(h);
//    }
}