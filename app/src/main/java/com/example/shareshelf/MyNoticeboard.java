package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MyNoticeboard extends AppCompatActivity {

    ImageView menu;

    RadioButton MyNoticeBoardPresent, MyNoticeBoardpast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_noticeboard);

        MyNoticeBoardPresent = findViewById(R.id.myNoticeboardPresent);
        MyNoticeBoardpast = findViewById(R.id.myNoticeboardPast);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNoticeboard.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}