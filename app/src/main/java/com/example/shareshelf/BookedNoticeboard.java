package com.example.shareshelf;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class BookedNoticeboard extends AppCompatActivity {

    RadioButton ActualPrenotation, BookedPast;
    ImageView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_noticeboard);

        ActualPrenotation = findViewById(R.id.BookedPresent);
        BookedPast = findViewById(R.id.BookedPast);
        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookedNoticeboard.this, Menu.class);
                startActivity(intent);
            }
        });
    }


}