package com.example.auction;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class custom_auction extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> d;
    ArrayList<String> e;
    ArrayList<String> f;


    SharedPreferences sh;
    LocalTime time;
    LocalDate date;
    String url1,date1,time1;
    String timeString;



    public custom_auction(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> e,ArrayList<String> f) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;
        this.f=f;
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
            gridView=inflator.inflate(R.layout.activity_custom_auction, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.prnam);
        ImageView i1=(ImageView) gridView.findViewById(R.id.im);
        TextView tv2=(TextView)gridView.findViewById(R.id.stprice);
        TextView tv3=(TextView)gridView.findViewById(R.id.bprice);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView18);
        Button jn=gridView.findViewById(R.id.rjct);






        jn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url1 = "http://" + sh.getString("ip", "") + ":5000/dattim";
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);


                        {
                            try {
//                        JSONObject jo = new JSONObject(response);
                                JSONArray ar=new JSONArray(response);
                                JSONObject jo=ar.getJSONObject(0);

                                date1=jo.getString("date1");
                                time1=jo.getString("time1");
//                        Toast.makeText(context, "+++++++++++" + time1 + "000000000" + date1, Toast.LENGTH_LONG).show();

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                time = LocalTime.parse(time1);
                                date = LocalDate.parse(date1);
//                        Toast.makeText(context, "+++++++++++" + time + "000000000" + date, Toast.LENGTH_LONG).show();


                                LocalDate cd = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    cd = LocalDate.now();
                                }

                                LocalTime ct = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    ct = LocalTime.now();
                                    int hour = ct.getHour();
                                    int minute = ct.getMinute();
                                    timeString = hour + ":" + minute;
                                }
                                try {

                                    if (date.equals(cd)) {
                                        // the time in the variable is the same as the current time
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        Toast.makeText(context, "times"+time+"==========="+timeString, Toast.LENGTH_SHORT).show();
                                        if (time.equals(ct) || time.isAfter(ct)) {
                                            Toast.makeText(context, "You can join only on the auction time", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Intent i = new Intent(context, auctiontest.class);
                                            i.putExtra("pid", f.get(position));
                                            context.startActivity(i);
                                            // the time in the variable is less than the current time
                                        }


                                    } else {
                                        Toast.makeText(context, "You can join only on the auction date", Toast.LENGTH_SHORT).show();

                                        // the time in the variable is not the same as the current time
                                    }


                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                    Intent i = new Intent(context, auctiontest.class);
                                    i.putExtra("pid", f.get(position));
                                    context.startActivity(i);
//                    Toast.makeText(context, ""+ex, Toast.LENGTH_SHORT).show();

                                }


                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }



                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"error",Toast.LENGTH_LONG).show();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("pid", f.get(position));
                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);


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
        tv3.setText(d.get(position));
        tv4.setText(e.get(position));


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);



        return gridView;

    }

}