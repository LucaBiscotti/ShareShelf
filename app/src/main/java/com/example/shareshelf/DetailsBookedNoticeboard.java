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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailsBookedNoticeboard extends AppCompatActivity {

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
        tv_category = findViewById(R.id.tv_category_bookedDetails);
        tv_dateStart = findViewById(R.id.tv_date_bookedDetails);
        sv_description = findViewById(R.id.sv_description_bookedDetails);
        tv_description = findViewById(R.id.tv_description_bookedDetails);
        tv_owner = findViewById(R.id.tv_owner_bookedDetails);
        tv_type = findViewById(R.id.tv_type_bookedDetails);
        tv_title = findViewById(R.id.tv_title_bookedDetails);
        tv_duration = findViewById(R.id.tv_duration_bookedDetails);
        tv_state = findViewById(R.id.tv_state_bookedDetails);

        idNoticeboard = fStore.collection("Annunci").document().getId();
        DocumentReference doc = fStore.collection("Annunci").document(idNoticeboard);

        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                    Map<String, Object> noticeboardPrint = new HashMap<>();
                    noticeboardPrint = task.getResult().getData();

                    if(noticeboardPrint.containsKey("categoria")){
                        Object val = noticeboardPrint.get("Categoria");
                        tv_category.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("dataInizio")){
                        Object val = noticeboardPrint.get("dataInizio");
                        tv_dateStart.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("descrizione")){
                        Object val = noticeboardPrint.get("descrizione");
                        tv_description.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("durata")){
                        Object val = noticeboardPrint.get("durata");
                        tv_duration.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("idCreatore")){
                        Object val = noticeboardPrint.get("idCreatore");
                        idCreator = val.toString();
                        fStore.collection("Utenti").document(idCreator).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                } else {
                                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                                    Map<String, Object> user = new HashMap<>();
                                    user = task.getResult().getData();
                                    if (user.containsKey("email")) {
                                        Object val = user.get("email");
                                        owner = val.toString();
                                        tv_owner.setText(owner);
                                    }

                                }
                            }
                        });

                        //tv_owner.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("stato")){
                        Object val = noticeboardPrint.get("stato");
                        tv_state.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("titolo")){
                        Object val = noticeboardPrint.get("titolo");
                        tv_title.setText(val.toString());
                    }
                    if(noticeboardPrint.containsKey("tipo")){
                        Object val = noticeboardPrint.get("tipo");
                        tv_type.setText(val.toString());
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( DetailsBookedNoticeboard.this, BookedNoticeboard.class);
                startActivity(intent);
            }
        });

    }
}