package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyProfile extends AppCompatActivity {

    ImageView menu, modifyAccount;
    Button mybooked, myfeedback, donation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, Menu.class);
                startActivity(intent);
            }
        });

        mybooked = findViewById(R.id.btn_toBookedNoticeboard);
        mybooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, MyNoticeboard.class);
                startActivity(intent);
            }
        });

        donation= findViewById(R.id.btn_toDonation);
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, PointsDonation.class);
                startActivity(intent);
            }
        });

        modifyAccount = findViewById(R.id.btnModifyAccount);
        modifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, ModifyProfile.class);
                startActivity(intent);
            }
        });
    }
}