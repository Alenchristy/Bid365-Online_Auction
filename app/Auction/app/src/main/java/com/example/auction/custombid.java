package com.example.auction;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class custombid extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> d;
    ArrayList<String> f,g;
    SharedPreferences sh;



    public custombid(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> f, ArrayList<String> g) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.f=f;
        this.g=g;
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
            gridView=inflator.inflate(R.layout.activity_custombid, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        ImageView i1=(ImageView) gridView.findViewById(R.id.im);
        TextView tv1=(TextView)gridView.findViewById(R.id.prnam);
        TextView tv2=(TextView)gridView.findViewById(R.id.stprice);
        TextView tv3=(TextView)gridView.findViewById(R.id.bprice);
        TextView contr=(TextView) gridView.findViewById(R.id.cont);
        TextView rc=(TextView) gridView.findViewById(R.id.rc);
        if(g.get(position).equalsIgnoreCase("rated"))
        {
             rc.setVisibility(View.GONE);
        }
        else {
            rc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ik = new Intent(context, rate.class);
                    ik.putExtra("bid", f.get(position));
                    context.startActivity(ik);
                    Toast.makeText(context, "" + f.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        contr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.15:5000/contract/" + f.get(position);


//                        String url = "http://192.168.43.15:5000/contract/?id="+f.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);

                Toast.makeText(context, ""+f.get(position), Toast.LENGTH_SHORT).show();

                RequestQueue queue = Volley.newRequestQueue(context);
                String url1 = "http://" + sh.getString("ip", "") + ":5000/contract";

                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the response string.
//                        Log.d("+++++++++++++++++", response);
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("pid", f.get(position));
//                        return params;
//                    }
//                };
//                queue.add(stringRequest);

            }
        });







//        tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(context,updateproduct.class);
//                i.putExtra("pid", d.get(position));
//                context.startActivity(i);
//
//            }
//        });

//        tv5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent id = new Intent(context,doubts.class);
//                id.putExtra("pid", d.get(position));
//                context.startActivity(id);
//            }
//        });


//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder ald=new AlertDialog.Builder(context);
//                ald.setTitle("Are you sure want to reject the offer")
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                try
//                                {
//                                    RequestQueue queue = Volley.newRequestQueue(context);
//                                    String url = "http://" + sh.getString("ip", "") + ":5000/rejectbid";
//
//                                    // Request a string response from the provided URL.
//                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                                        @Override
//                                        public void onResponse(String response) {
//                                            // Display the response string.
//                                            Log.d("+++++++++++++++++", response);
//                                            try {
//                                                JSONObject json = new JSONObject(response);
//                                                String res = json.getString("task");
//
//                                                if (res.equalsIgnoreCase("success")) {
//
//                                                    Toast.makeText(context, "succesfully rejected", Toast.LENGTH_SHORT).show();
//                                                    Intent i = new Intent(context, adproducts.class);
//                                                    context.startActivity(i);
//
//
//                                                } else {
//
//                                                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show();
//
//                                                }
//                                            } catch (JSONException e) {
//                                                Toast.makeText(context, "==" + e, Toast.LENGTH_SHORT).show();
//
//                                                e.printStackTrace();
//                                            }
//
//
//                                        }
//                                    }, new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//
//
//                                            Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
//                                        }
//                                    }) {
//                                        @Override
//                                        protected Map<String, String> getParams() {
//                                            Map<String, String> params = new HashMap<String, String>();
//                                            params.put("pid", f.get(position));
//
//
//                                            return params;
//                                        }
//                                    };
//                                    queue.add(stringRequest);
//
//                                }
//                                catch(Exception e)
//                                {
//                                    Toast.makeText(context.getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                        })
//                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//
//
//                            }
//                        });
//
//                AlertDialog al=ald.create();
//                al.show();
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

        thumb_u = new java.net.URL("http://" + sh.getString("ip", "") + ":5000/media/" + a.get(position));
        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
        i1.setImageDrawable(thumb_d);

    } catch (Exception e) {
        Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
        Log.d("errsssssssssssss", "" + e);
    }


    tv1.setText(b.get(position));
    tv2.setText(c.get(position));
    tv3.setText(d.get(position));

    tv1.setTextColor(Color.BLACK);
    tv2.setTextColor(Color.BLACK);
    tv3.setTextColor(Color.BLACK);




        return gridView;

    }

}





