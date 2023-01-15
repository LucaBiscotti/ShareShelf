package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class PointsDonation extends AppCompatActivity {

    ImageView menu;
    Button cancel, send;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String myId, friendId, emailFriend;
    EditText inputEmail, pointsToDonate;
    Integer pointsDonated, myActualPoints, friendActualPoints;
    String myName, mySurname, myEmail, myAddress, myPhoneNumber, myRating;
    String friendName, friendSurname, friendAddress, friendPhoneNumber, friendRating;
    TextView printMyPoints;

    /*
    Ctrl - Alt - o per cancellare tutti gli import che alla fine non sono serviti
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_donation);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        inputEmail = findViewById(R.id.inputEmailReciver);
        pointsToDonate = findViewById(R.id.et_pointsToDonate);
        printMyPoints = findViewById(R.id.actualPoints);

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

        Intent data = getIntent();
        printMyPoints.setText(data.getStringExtra("mypoints"));
        myId = data.getStringExtra("id");
        myEmail = data.getStringExtra("email");



        send = findViewById(R.id.btnSendPoints);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailFriend = inputEmail.getText().toString().trim();
                pointsDonated = Integer.parseInt(pointsToDonate.getText().toString().trim());

                CollectionReference usersRef = fStore.collection("Utenti");
                Query query = usersRef.whereEqualTo("email", emailFriend);

                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userId = document.getId();
                                final DocumentReference sfDocRef = fStore.collection("Utenti").document(myId);
                                final DocumentReference sfDocRefFriend = fStore.collection("Utenti").document(userId);

                                fStore.runTransaction(new Transaction.Function<Void>() {
                                            @Override
                                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                                                DocumentSnapshot snapshot2 = transaction.get(sfDocRefFriend);

                                                // Note: this could be done without a transaction
                                                //       by updating the population using FieldValue.increment()
                                                if(pointsDonated>snapshot.getDouble("points")){
                                                    pointsDonated = 0;
                                                    Toast.makeText(PointsDonation.this, "Non hai punti a sufficienza", Toast.LENGTH_SHORT).show();
                                                }
                                                double newPoints = snapshot.getDouble("points") - pointsDonated;
                                                transaction.update(sfDocRef, "points", newPoints);

                                                double newFriendPoints = snapshot2.getDouble("points") + pointsDonated;
                                                transaction.update(sfDocRefFriend, "points", newFriendPoints);

                                                // Success
                                                return null;
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "Transaction success!");
                                                Intent intent = new Intent(PointsDonation.this, MyProfile.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Transaction failure.", e);
                                            }
                                        });

                            }
                        }
                    }
                });

            }
        });
    }


}