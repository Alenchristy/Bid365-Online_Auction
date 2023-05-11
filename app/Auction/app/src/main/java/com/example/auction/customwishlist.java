package com.example.auction;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class customwishlist extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> pid;

    SharedPreferences sh;



    public customwishlist(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c,  ArrayList<String> pid) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;

        this.pid=pid;
        sh=PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_customwishlist, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.prnam);
        ImageView i1=(ImageView) gridView.findViewById(R.id.im);
        TextView tv2=(TextView)gridView.findViewById(R.id.stprice);

        CardView cv=(CardView) gridView.findViewById(R.id.cv);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik=new Intent(context,productview.class);
                ik.putExtra("pid",pid.get(position));
                context.startActivity(ik);
            }
        });


//        if(d.get(position).equals("0"))
//        {
//            tv3.setBackgroundResource(R.drawable.favorite);
//        }
//        else
//        {
//            tv3.setBackgroundResource(R.drawable.wish);
//        }
//
//        tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(d.get(position).equals("0"))
//                {
//                    d.set(position, "1");
//                    tv3.setBackgroundResource(R.drawable.wish);
//
//                    RequestQueue queue = Volley.newRequestQueue(context);
//                    String url = "http://" + sh.getString("ip", "") + ":5000/insert_wish";
//
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Display the response string.
//                            Log.d("+++++++++++++++++", response);
//                            try {
//                                JSONObject json = new JSONObject(response);
//                                String res = json.getString("result");
//
//
//                            } catch (JSONException e) {
////                                Toast.makeText(context, "==" + e, Toast.LENGTH_SHORT).show();
//
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//
////                            Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("pid", pid.get(position));
//                            params.put("lid", sh.getString("lid",""));
//
//                            return params;
//                        }
//                    };
//                    queue.add(stringRequest);
//
//
//
//                }
//                else
//                {
//                    d.set(position, "0");
//                    tv3.setBackgroundResource(R.drawable.favorite);
//                    RequestQueue queue = Volley.newRequestQueue(context);
//                    String url = "http://" + sh.getString("ip", "") + ":5000/delete_wish";
//
//                    // Request a string response from the provided URL.
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Display the response string.
//                            Log.d("+++++++++++++++++", response);
//                            try {
//                                JSONObject json = new JSONObject(response);
//                                String res = json.getString("result");
//
//
//                            } catch (JSONException e) {
////                                Toast.makeText(context, "==" + e, Toast.LENGTH_SHORT).show();
//
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//
////                            Toast.makeText(context, "Error" + error, Toast.LENGTH_LONG).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("pid", pid.get(position));
//                            params.put("lid", sh.getString("lid",""));
//
//                            return params;
//                        }
//                    };
//                    queue.add(stringRequest);
//
//                }
//
//
//            }
//        });


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        java.net.URL thumb_u;
        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/media/"+a.get(position));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            i1.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
//            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            Log.d("errsssssssssssss",""+e);
        }


        tv1.setText(b.get(position));
        tv2.setText(c.get(position));

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);



        return gridView;

    }

}
