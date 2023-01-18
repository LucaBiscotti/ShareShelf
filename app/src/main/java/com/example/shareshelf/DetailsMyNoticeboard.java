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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class DetailsMyNoticeboard extends AppCompatActivity {
    Button btn_goToListCandidate, btn_goToModifyNoticeboard, btn_finish;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String idCandidate, idNoticeboard, state;
    TextView tv_category, tv_description, tv_type, tv_title, tv_duration, tv_dateStart, tv_state, tv_name, tv_lastname;
    ScrollView sv_description;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_my_noticeboard);

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
        tv_name = findViewById(R.id.tv_nameCandidate);
        tv_lastname = findViewById(R.id.tv_lastnameCandidate);
        btn_finish = findViewById(R.id.btn_finish);

        idNoticeboard = fStore.collection("Annunci").document().getId();
        DocumentReference doc = fStore.collection("Annunci").document(idNoticeboard);

        String title = getIntent().getStringExtra("Titolo");
        String type = getIntent().getStringExtra("Tipo");
        String category = getIntent().getStringExtra("Categoria");
        String date = getIntent().getStringExtra("Data");
        String durata = getIntent().getStringExtra("Durata");
        String statey = getIntent().getStringExtra("Stato");
        String description = getIntent().getStringExtra("Descrizione");
        String idAnnuncio = getIntent().getStringExtra("IdAnnuncio");
        String idBooking = getIntent().getStringExtra("idPrenotazione");

        tv_title.setText(title);
        tv_type.setText(type);
        tv_category.setText(category);
        tv_dateStart.setText(date);
        tv_duration.setText(durata);
        tv_state.setText(statey);
        tv_description.setText(description);


        CollectionReference usersRef = fStore.collection("Prenotazioni");
        Query query = usersRef.whereEqualTo("idNoticeboard", idNoticeboard);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("Messaggio_test", "lol è noi");
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userId = document.getString("idCandidate");
                        Log.d("Messaggio_test", "lol è lui" + userId);
                        final DocumentReference sfDocRefFriend = fStore.collection("Utenti").document(userId);
                        sfDocRefFriend.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                                    Map<String, Object> userCan = new HashMap<>();
                                    userCan = task.getResult().getData();

                                    if(userCan.containsKey("name")){
                                        Object val = userCan.get("name");
                                        tv_name.setText(val.toString());
                                    }
                                    if(userCan.containsKey("surname")){
                                        Object val = userCan.get("surname");
                                        tv_lastname.setText(val.toString());
                                    }
                                }
                            }
                        });


                    }
                }
                else{
                    tv_name.setText("Nessuno prenotato");
                    tv_lastname.setText("Nessuno prenotato");
                }
            }
        });


/*
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DocumentReference sfDocRefFriend = fStore.collection("Utenti").document(idCandidate);
                fStore.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                DocumentSnapshot snapshot2 = transaction.get(sfDocRefFriend);

                                // Note: this could be done without a transaction
                                //       by updating the population using FieldValue.increment()
                                Integer pointsDonated = Integer.parseInt(durata);

                                double newFriendPoints = snapshot2.getDouble("points") + pointsDonated;
                                transaction.update(sfDocRefFriend, "points", newFriendPoints);

                                // Success
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "Transaction success!");
                                Intent intent = new Intent(DetailsMyNoticeboard.this, MyNoticeboard.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Transaction failure.", e);
                            }
                        });


                fStore.collection("Annunci").document(idNoticeboard)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });


                /*
                fStore.collection("Prenotazioni").document(idBooking)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });

            }
        });


 */


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