package com.example.auction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.SearchView;
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

public class viewcatproduct extends AppCompatActivity {
    GridView gw;
    String url,url1;

    ArrayList<String> pro,img,price,pid,status,loc;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcatproduct);
        gw = findViewById(R.id.gv);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        url1 ="http://"+sh.getString("ip", "") + ":5000/search_view";

        url ="http://"+sh.getString("ip", "") + ":5000/viewcatprodct1";
        RequestQueue queue = Volley.newRequestQueue(viewcatproduct.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show()

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
                        loc.add(jo.getString("loc"));
                        pid.add(jo.getString("id"));
                        status.add(jo.getString("status"));



                    }


                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);



//                    ls.setDivider(null);
//                    ls.setDividerHeight(0);
//                    ls.setHorizontalScrollBarEnabled(false);

                    gw.setAdapter(new customproduct(viewcatproduct.this,img,pro,price,status,loc,pid));
//                    ls.setOnItemClickListener(userhome.this);

                } catch (Exception e) {
                    Toast.makeText(viewcatproduct.this, "err===="+e, Toast.LENGTH_SHORT).show();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewcatproduct.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("lid",sh.getString("lid",""));
                params.put("cid",getIntent().getStringExtra("cid"));

                return params;
            }
        };
        queue.add(stringRequest);

    }
}