package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;

public class Menu extends AppCompatActivity {

    View to_bacheca, to_myActivity, to_profile, to_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_drawer, menu);
        to_bacheca = findViewById(R.id.nav_home);
        to_profile = findViewById(R.id.nav_profile);
        to_myActivity = findViewById(R.id.nav_myActivity);
        to_logout = findViewById(R.id.nav_logout);

        to_bacheca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Bacheca.class);
                startActivity(intent);
            }
        });

        to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MyProfile.class);
                startActivity(intent);
            }
        });

        to_myActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MyNoticeboard.class);
                startActivity(intent);
            }
        });

        to_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Logout.class);
                startActivity(intent);
            }
        });

        return true;
    }
}