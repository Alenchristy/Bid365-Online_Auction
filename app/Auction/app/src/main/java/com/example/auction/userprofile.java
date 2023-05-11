package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class userprofile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , AdapterView.OnItemClickListener {
    BottomNavigationView bottomNavigationView;
    ListView ls;
    int a;
    String pname;
    SharedPreferences sh;
    String nam[] = {"View and update profile","Watch list","Feedbacks","Log Out", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);



        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(userprofile.this);

        ls=findViewById(R.id.list1);
        ArrayAdapter<String> ar =new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,nam);
        ls.setAdapter(ar);
        ls.setOnItemClickListener(userprofile.this);

//        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//        String url1 = "http://" + sh.getString("ip", "") + ":5000/productview";
//        RequestQueue queue = Volley.newRequestQueue(userprofile.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
////                // Display the response string.
////                Log.d("+++++++++++++++++",response);
//                try {
//                    JSONArray ar=new JSONArray(response);
//                    {
//                        JSONObject jo=ar.getJSONObject(0);
//                        pname.setText(jo.getString("pname"));
//
//
//
//                    }
//                    if (android.os.Build.VERSION.SDK_INT > 9) {
//                        StrictMode.ThreadPolicy policy =
//                                new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                        StrictMode.setThreadPolicy(policy);
//                    }
//
//
//                } catch (JSONException e) {
////                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("lid", sh.getString("lid", ""));
//                return params;
//            }
//        };
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);




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

            case R.id.sell:
                Intent k = new Intent(getApplicationContext(), sell.class);
                startActivity(k);
                return true;

            case R.id.bids:
                Intent m = new Intent(getApplicationContext(), bid.class);
                startActivity(m);
                return true;




        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        a = i;
        if (a == 0) {
            Intent k = new Intent(getApplicationContext(), updateprofile.class);
            startActivity(k);
        }

        else if(a == 1) {
            Intent yk = new Intent(getApplicationContext(), wishlist.class);
            startActivity(yk);
        }
        else if(a == 2) {
            Intent yk = new Intent(getApplicationContext(), feedback.class);
            startActivity(yk);

        }
        else if(a == 3) {
            onBackPressed();
            Intent yk = new Intent(getApplicationContext(), MainActivity4.class);
            startActivity(yk);

        }



    }
    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), userhome.class);
        startActivity(h);
    }
}