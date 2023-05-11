package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
//import android.support.v4.view.ViewPager;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Map;


public class adproducts extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;


    ListView ls;
    String url;
    ArrayList<String> pid,status,loc;
    ArrayList<String> pro,img,price,date,time,stprice,bprice;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adproducts);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(adproducts.this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



//        ls = findViewById(R.id.lv1);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


//        Toast.makeText(this, "======", Toast.LENGTH_SHORT).show();





        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_adproducts);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of welcome sliders
        layouts = new int[]{

                R.layout.activity_sell,
                R.layout.activity_registerdauction

        };


        // adding bottom dots
//        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);


        viewPager.setCurrentItem(0);






        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page if true launch MainActivity
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                    launchHomeScreen();
                } else {
                    launchHomeScreen();
                }
            }
        });



        btnNext.setTextColor(Color.WHITE);
        btnSkip.setTextColor(Color.BLACK);






    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("?"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(true);
        prefManager.setFirstTimeLaunch(false);
//        startActivity(new Intent(auctions.this, MainActivity.class));
//        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {

//            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
//                Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_LONG).show();
                // last page. make button text to GOT IT
                btnNext.setTextColor(Color.BLACK);
                btnSkip.setTextColor(Color.WHITE);

                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                GridView gv=findViewById(R.id.gv);


                Button b=findViewById(R.id.btn);
                ListView ls1 = findViewById(R.id.list2);
                url ="http://"+sh.getString("ip", "") + ":5000/viewuserprodct";
                RequestQueue queue = Volley.newRequestQueue(adproducts.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();

                            JSONArray ar=new JSONArray(response);

                            pro= new ArrayList<>();
                            price= new ArrayList<>();
                            img=new ArrayList<>();
                            pid=new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);

                                pro.add(jo.getString("pname"));
                                price.add(jo.getString("price"));
                                img.add(jo.getString("image"));
                                pid.add(jo.getString("id"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            ls1.setAdapter(new CustomSell(adproducts.this,img,pro,price,pid));
//                            ls.setOnItemClickListener(sell.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(adproducts.this, "err"+error, Toast.LENGTH_SHORT).show();
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

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getApplicationContext(),adproduct.class);
                        startActivity(i);
                    }
                });






            }
            else {
                btnNext.setTextColor(Color.WHITE);
                btnSkip.setTextColor(Color.BLACK);
                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                GridView gv=findViewById(R.id.gv);


                Button b=findViewById(R.id.btn);
                ListView ls1 = findViewById(R.id.list2);
                url ="http://"+sh.getString("ip", "") + ":5000/viewuserprodct";
                RequestQueue queue = Volley.newRequestQueue(adproducts.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();

                            JSONArray ar=new JSONArray(response);

                            pro= new ArrayList<>();
                            price= new ArrayList<>();
                            img=new ArrayList<>();
                            pid=new ArrayList<>();


                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);

                                pro.add(jo.getString("pname"));
                                price.add(jo.getString("price"));
                                img.add(jo.getString("image"));
                                pid.add(jo.getString("id"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            ls1.setAdapter(new CustomSell(adproducts.this,img,pro,price,pid));
//                            ls.setOnItemClickListener(sell.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(adproducts.this, "err"+error, Toast.LENGTH_SHORT).show();
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

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getApplicationContext(),adproduct.class);
                        startActivity(i);
                    }
                });

            }
            // still pages are left



            ls=findViewById(R.id.list1);

            url ="http://"+sh.getString("ip", "") + ":5000/soldproduct";


            RequestQueue queue = Volley.newRequestQueue(adproducts.this);
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

                        ls.setAdapter(new custom_sold(adproducts.this,img,pro,stprice,bprice,pid));
//                        ls.setOnItemClickListener(adproducts.this);

                    } catch (Exception e) {
                        Log.d("=========", e.toString());
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(adproducts.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        public void onPageScrolled(int arg0, float arg1, int arg2) {



//            Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_LONG).show();
            // last page. make button text to GOT IT
//            btnNext.setTextColor(Color.BLACK);
//            btnSkip.setTextColor(Color.WHITE);

            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            GridView gv=findViewById(R.id.gv);


            Button b=findViewById(R.id.btn);
            ListView ls1 = findViewById(R.id.list2);
            url ="http://"+sh.getString("ip", "") + ":5000/viewuserprodct";
            RequestQueue queue = Volley.newRequestQueue(adproducts.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("+++++++++++++++++",response);
                    try {
//                    Toast.makeText(sell.this, ""+response, Toast.LENGTH_SHORT).show();

                        JSONArray ar=new JSONArray(response);

                        pro= new ArrayList<>();
                        price= new ArrayList<>();
                        img=new ArrayList<>();
                        pid=new ArrayList<>();


                        for(int i=0;i<ar.length();i++)
                        {
                            JSONObject jo=ar.getJSONObject(i);

                            pro.add(jo.getString("pname"));
                            price.add(jo.getString("price"));
                            img.add(jo.getString("image"));
                            pid.add(jo.getString("id"));



                        }

                        // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                        //lv.setAdapter(ad);

                        ls1.setAdapter(new CustomSell(adproducts.this,img,pro,price,pid));
//                            ls.setOnItemClickListener(sell.this);

                    } catch (Exception e) {
                        Log.d("=========", e.toString());
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(adproducts.this, "err"+error, Toast.LENGTH_SHORT).show();
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

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getApplicationContext(),adproduct.class);
                    startActivity(i);
                }
            });




        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    // Making notification bar transparent

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
//
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent h = new Intent(getApplicationContext(),userhome.class);
                startActivity(h);
                return true;

            case R.id.auctions:
                bottomNavigationView.setSelectedItemId(R.id.auctions);
                Intent i = new Intent(getApplicationContext(),auctions.class);
                startActivity(i);
                return true;

            case R.id.sell:
                Intent k = new Intent(getApplicationContext(),sell.class);
                startActivity(k);
                return true;

            case R.id.bids:
                Intent m = new Intent(getApplicationContext(),bid.class);
                startActivity(m);
                return true;

            case R.id.profile:
                Intent u = new Intent(getApplicationContext(),userprofile.class);
                startActivity(u);
                return true;

        }
        return false;
    }
}
