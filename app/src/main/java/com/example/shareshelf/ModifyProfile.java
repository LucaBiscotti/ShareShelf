package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ModifyProfile extends AppCompatActivity {

    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        save = findViewById(R.id.btnCancelModify);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyProfile.this, MyProfile.class);
                startActivity(intent);
            }
        });

        cancel = findViewById(R.id.btn_saveModify);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyProfile.this, MyProfile.class);
                startActivity(intent);
            }
        });
    }
}