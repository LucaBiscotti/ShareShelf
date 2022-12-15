package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Logout extends AppCompatActivity {

    ImageView menu;
    Button logout, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logout.this, Menu.class);
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.btnYesLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logout.this, Login.class);
                startActivity(intent);
            }
        });

        cancel = findViewById(R.id.btnNoLogout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logout.this, Bacheca.class);
                startActivity(intent);
            }
        });
    }
}