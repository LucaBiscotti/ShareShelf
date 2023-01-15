package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailsMyNoticeboard extends AppCompatActivity {
    Button btn_goToListCandidate, btn_goToModifyNoticeboard;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String myId, idNoticeboard, state;
    String category, description, owner, idCreator, type, title, duration, dateStart;
    TextView tv_category, tv_description, tv_owner, tv_type, tv_title, tv_duration, tv_dateStart, tv_state;
    ScrollView sv_description;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_noticeboard);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        back = findViewById(R.id.go_back);
        tv_category = findViewById(R.id.tv_category_mydetails);
        tv_dateStart = findViewById(R.id.tv_date_mydetails);
        sv_description = findViewById(R.id.sv_description_mydetails);
        tv_description = findViewById(R.id.tv_description_mydetails);
        tv_type = findViewById(R.id.tv_type_mydetails);
        tv_title = findViewById(R.id.tv_title_mydetails);
        tv_duration = findViewById(R.id.tv_duration_mydetails);
        tv_state = findViewById(R.id.tv_state_mydetails);
        btn_goToModifyNoticeboard = findViewById(R.id.btn_goTo_modify_noticeboard);

        idNoticeboard = fStore.collection("Annunci").document().getId();
        Log.d("Messaggio_test", idNoticeboard);
        DocumentReference doc = fStore.collection("Annunci").document(idNoticeboard);

        String title = getIntent().getStringExtra("Titolo");
        String type = getIntent().getStringExtra("Tipo");
        String category = getIntent().getStringExtra("Categoria");
        String date = getIntent().getStringExtra("Data");
        String durata = getIntent().getStringExtra("Durata");
        String creator = getIntent().getStringExtra("Creatore");
        String statey = getIntent().getStringExtra("Stato");
        String description = getIntent().getStringExtra("Descrizione");


        tv_title.setText(title);
        tv_type.setText(type);
        tv_category.setText(category);
        tv_dateStart.setText(date);
        tv_duration.setText(durata);
        tv_state.setText(statey);
        tv_description.setText(description);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetailsMyNoticeboard.this, MyNoticeboard.class);
                startActivity(intent);
            }
        });

        btn_goToModifyNoticeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetailsMyNoticeboard.this, ModifyNoticeboard.class);
                startActivity(intent);
            }
        });

    }
}