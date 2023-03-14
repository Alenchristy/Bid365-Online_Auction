package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class userprofile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , AdapterView.OnItemClickListener {
    BottomNavigationView bottomNavigationView;
    ListView ls;
    int a;
    String nam[] = {"View and update profile","Chats","Watch list","Notifications","Feedbacks","Log Out", ""};

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

        else {
            Intent yk = new Intent(getApplicationContext(), wishlist.class);
            startActivity(yk);

        }

    }
    @Override
    public void onBackPressed() {
        Intent h = new Intent(getApplicationContext(), userhome.class);
        startActivity(h);
    }
}