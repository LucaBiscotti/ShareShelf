package com.example.shareshelf;

import static com.example.shareshelf.R.menu.activity_main_drawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;

public class Menu extends AppCompatActivity {

    View to_bacheca, to_profile, to_myActivity, to_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(activity_main_drawer, menu);
        to_bacheca = findViewById(R.id.nav_home);
        to_profile = findViewById(R.id.nav_profile);
        to_myActivity = findViewById(R.id.nav_myActivity);
        to_logout = findViewById(R.id.nav_logout);

        return true;
    }
}