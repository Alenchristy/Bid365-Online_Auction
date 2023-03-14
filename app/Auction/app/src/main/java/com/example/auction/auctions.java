package com.example.auction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class auctions extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auctions);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(auctions.this);

//        bottomNavigationView.setSelectedItemId(R.id.auctions);
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