package com.example.auction;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomSell extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> d;
    SharedPreferences sh;



    public CustomSell(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
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
            gridView=inflator.inflate(R.layout.activity_custom_sell, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.prnam);
        ImageView i1=(ImageView) gridView.findViewById(R.id.im);
        TextView tv2=(TextView)gridView.findViewById(R.id.stprice);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView6);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView7);
        TextView tv5=(TextView)gridView.findViewById(R.id.vdoubt);




        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context,updateproduct.class);
                i.putExtra("pid", d.get(position));
                context.startActivity(i);

            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent id = new Intent(context,doubts.class);
                id.putExtra("pid", d.get(position));
                context.startActivity(id);
            }
        });


        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder ald=new AlertDialog.Builder(context);
                ald.setTitle("Are you sure want to delete?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try
                                {
                                    RequestQueue queue = Volley.newRequestQueue(context);
                                    String url = "http://" + sh.getString("ip", "") + ":5000/deleteproduct";

                                    // Request a string response from the provided URL.
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Display the response string.
                                            Log.d("+++++++++++++++++", response);
                                            try {
                                                JSONObject json = new JSONObject(response);
                                                String res = json.getString("task");

                                                if (res.equalsIgnoreCase("success")) {

                                                    Toast.makeText(context, "succesfully deleted", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(context, sell.class);
                                                    context.startActivity(i);


                                                } else {

                                                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (JSONException e) {
                                                Toast.makeText(context, "==" + e, Toast.LENGTH_SHORT).show();

                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {


                                            Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("pid", d.get(position));
                                            return params;
                                        }
                                    };
                                    queue.add(stringRequest);




                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(context.getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                            }
                        });

                AlertDialog al=ald.create();
                al.show();

            }
        });

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
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            Log.d("errsssssssssssss",""+e);
        }


        tv1.setText(b.get(position));
        tv2.setText(c.get(position));

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);



        return gridView;

    }

}





