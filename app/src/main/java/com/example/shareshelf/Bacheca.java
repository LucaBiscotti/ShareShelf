package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.ImageView;

public class Bacheca extends AppCompatActivity {

    ImageView menu, filter;
    View addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, Menu.class);
                startActivity(intent);
            }
        });

        filter = findViewById(R.id.goToFilter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, Filter.class);
                startActivity(intent);
            }
        });

        addNotice = findViewById(R.id.btnAdd);
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, CreateNoticeboard.class);
                startActivity(intent);
            }
        });
    }


}