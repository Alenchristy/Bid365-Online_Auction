package com.example.auction;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class customdoubts extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;

    SharedPreferences sh;



    public customdoubts(Context applicationContext, ArrayList<String> a, ArrayList<String> b) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;

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
            gridView=inflator.inflate(R.layout.activity_customdoubts, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.prnam);
        CardView cv=(CardView) gridView.findViewById(R.id.cv);
//        ImageView i1=(ImageView) gridView.findViewById(R.id.imageView2);





        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        tv1.setText(a.get(position));


        tv1.setTextColor(Color.BLACK);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("uid",b.get(position));
                ed.commit();
                Intent ik=new Intent(context,Test.class);
                ik.putExtra("pid",sh.getString("pid",""));
                ik.putExtra("ty","o");
                context.startActivity(ik);
            }
        });




        return gridView;

    }

}
