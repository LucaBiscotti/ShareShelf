package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class DetailsNoticeboard extends AppCompatActivity {
    Button btn_booking;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String myId, idNoticeboard, state;
    String category, description, owner, idCreator, type, title, duration, dateStart;
    TextView tv_category, tv_description, tv_owner, tv_type, tv_title, tv_duration, tv_dateStart, tv_state;
    ScrollView sv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_noticeboard);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btn_booking = findViewById(R.id.btn_booking);
        tv_category = findViewById(R.id.tv_category_details);
        tv_dateStart = findViewById(R.id.tv_date_details);
        sv_description = findViewById(R.id.sv_description_details);
        tv_description = findViewById(R.id.tv_description_details);
        tv_owner = findViewById(R.id.tv_owner_details);
        tv_type = findViewById(R.id.tv_type_details);
        tv_title = findViewById(R.id.tv_title_details);
        tv_duration = findViewById(R.id.tv_duration_details);
        tv_state = findViewById(R.id.tv_state_details);

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

        /*
        Log.e("firebase", "Qua " + creator);
        fStore.collection("Utenti").document(creator).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

         */

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myId = fAuth.getCurrentUser().getUid();
                DocumentReference df = fStore.collection("Utenti").document(myId);


                Booking booking = new Booking(doc, df);
                state = "Prenotato";
                fStore.collection("Prenotazioni").document()
                        .set(booking)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");

                                Map<String, Object> edited = new HashMap<>();

                                if(state != null)
                                    edited.put("stato", state);

                                DocumentReference df = fStore.collection("Annunci").document(idNoticeboard);

                                df
                                        .update(edited)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                                Toast.makeText(DetailsNoticeboard.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent( DetailsNoticeboard.this, Bacheca.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error updating document", e);
                                                Toast.makeText(DetailsNoticeboard.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });

            }
        });

    }
}