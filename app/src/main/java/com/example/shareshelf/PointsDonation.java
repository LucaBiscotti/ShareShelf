package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PointsDonation extends AppCompatActivity {

    ImageView menu;
    Button cancel, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_donation);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PointsDonation.this, Menu.class);
                startActivity(intent);
            }
        });

        cancel = findViewById(R.id.btnCancelPoints);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PointsDonation.this, MyProfile.class);
                startActivity(intent);
            }
        });

        send = findViewById(R.id.btnSendPoints);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PointsDonation.this, MyProfile.class);
                startActivity(intent);
            }
        });
    }
}