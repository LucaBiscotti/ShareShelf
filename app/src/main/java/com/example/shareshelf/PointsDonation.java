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
        //findCurrentUserData();
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
                                                double newPoints = snapshot.getDouble("points") - pointsDonated;
                                                transaction.update(sfDocRef, "points", newPoints);

                                                double newFriendPoints = snapshot.getDouble("points") + pointsDonated;
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

                                //doTransactionOfPoints();

                                // do something with the user ID
                                //friendId = userId;
                            }
                        }
                    }
                });





            }
        });
    }

    private void doTransactionOfPoints() {



    }


    private void sendPointsToSomeone() {


        // --> FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // --> String email = user.getEmail();

        emailFriend = inputEmail.getText().toString().trim();
        pointsDonated = Integer.parseInt(pointsToDonate.getText().toString().trim());



// Query the users collection for a document with a field email that matches the current user's email
        fStore.collection("Utenti").whereEqualTo("email", emailFriend)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Get the matching document
                            QuerySnapshot querySnapshot = task.getResult();
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);

                            // Get the user ID from the document
                            friendId = document.getId();


                        } else {
                            // Handle the error
                            Toast.makeText(PointsDonation.this, "Failed to get friend id", Toast.LENGTH_SHORT).show();

                            Log.e("firebase", "Error getting data", task.getException());

                        }
                    }
                });

        //get friend point to modify
        fStore.collection("Utenti").document(friendId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(PointsDonation.this, "Failed to get friend points" , Toast.LENGTH_SHORT).show();


                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                    Map<String, Object> user = new HashMap<>();
                    user = task.getResult().getData();

                    if(user.containsKey("points")){
                        Object val = user.get("points");
                        friendActualPoints = Integer.parseInt(val.toString());
                        //points.setText(val.toString());
                    }


                   /* if(user.containsKey("name")){
                        Object val = user.get("name");
                        friendName = val.toString();
                    }
                    if(user.containsKey("surname")){
                        Object val = user.get("surname");
                        friendSurname = val.toString();
                    }
                    if(user.containsKey("address")){
                        Object val = user.get("address");
                        friendAddress = val.toString();
                    }
                    if(user.containsKey("phoneNumber")){
                        Object val = user.get("phoneNumber");
                        friendPhoneNumber = val.toString();
                    }*/
                   /* if(user.containsKey("rating")){
                        Object val = user.get("rating");
                        friendRating = val.toString();
                    }*/

                }
            }
        });

        //myActualPoints -= pointsDonated;
        //friendActualPoints += pointsDonated;

        //Update my user and friend user

        // Get a reference to the user's document
        // --> FirebaseFirestore db = FirebaseFirestore.getInstance();
        // update my user

        //DocumentReference userRef = fStore.collection("Utenti").document(myId);
       // Users updates = new Users(mySurname, myEmail, myAddress, myName, "password1", myActualPoints, myPhoneNumber);

        // Update one field, creating the document if it does not already exist.
        Map<String, Object> data = new HashMap<>();
        data.put("points", myActualPoints - pointsDonated);
       /* fStore.collection("Utenti").document(myId)
                .set(data, SetOptions.merge());*/

        Map<String, Object> friendData = new HashMap<>();
        data.put("points", friendActualPoints + pointsDonated);
        /*fStore.collection("Utenti").document(friendId)
                .set(friendData, SetOptions.merge());*/


        DocumentReference washingtonRef = fStore.collection("Utenti").document(myId);

// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });


        DocumentReference docref = fStore.collection("Utenti").document(friendId);

// Set the "isCapital" field of the city 'DC'
        docref
                .update(friendData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });



        /*fStore.collection("Utenti").document(myId)
                .update("points", (myActualPoints - pointsDonated));
        fStore.collection("Utenti").document(friendId)
                .update("points", (friendActualPoints + pointsDonated));*/



        // Update the user's data
       /* Map<String, Object> updates = new HashMap<>();
        updates.put("name", myName);
        updates.put("surname", mySurname);
        updates.put("email", myEmail);
        updates.put("points", myActualPoints);
        updates.put("address", myAddress);
        updates.put("phoneNumber", myPhoneNumber);*/
        //updates.put("rating", myRating);

        /*userRef.update((Map<String, Object>) updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update successful
                        Toast.makeText(PointsDonation.this, "Update myprofile successfull" , Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Update failed
                        Toast.makeText(PointsDonation.this, "Failed to update account" +e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });*/
      /*  fStore.collection("Utenti").document(myId)
                .set(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PointsDonation.this, "My user updated", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PointsDonation.this, "Failed to update my account" +e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });*/
       /* final DocumentReference sfDocRef = fStore.collection("Utenti").document(myId);

        fStore.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(sfDocRef);

                        // Note: this could be done without a transaction
                        //       by updating the population using FieldValue.increment()
                        //double newPopulation = snapshot.getDouble("population") + 1;
                        //transaction.update(sfDocRef, "population", newPopulation);
                        transaction.update(sfDocRef, "name", myName);
                        transaction.update(sfDocRef, "surname", mySurname);
                        transaction.update(sfDocRef, "email", myEmail);
                        transaction.update(sfDocRef, "points", myActualPoints);
                        transaction.update(sfDocRef, "address", myAddress);
                        transaction.update(sfDocRef, "phoneNumber", myPhoneNumber);
                        //transaction.update(sfDocRef, "rating", myRating);


                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Transaction success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Transaction failure.", e);
                    }
                });
*/

        // update friend user
        //DocumentReference df = fStore.collection("Utenti").document(friendId);

        // Update the user's data
        //Users friendUpdate = new Users(friendSurname, emailFriend, friendAddress, friendName, "password1", friendActualPoints, friendPhoneNumber);


        /*Map<String, Object> friendUpdate = new HashMap<>();
        friendUpdate.put("name", friendName);
        friendUpdate.put("surname", friendSurname);
        friendUpdate.put("email", emailFriend);
        friendUpdate.put("points", friendActualPoints);
        friendUpdate.put("address", friendAddress);
        friendUpdate.put("phoneNumber", friendPhoneNumber);*/
        //friendUpdate.put("rating", friendRating);

       /* userRef.update((Map<String, Object>) friendUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update successful
                        Toast.makeText(PointsDonation.this, "Update friend's profile successfull" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PointsDonation.this, MyProfile.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Update failed
                        Toast.makeText(PointsDonation.this, "Failed to update account" +e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });*/
        /*fStore.collection("Utenti").document(friendId)
                .set(friendUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PointsDonation.this, "Friend user updated", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PointsDonation.this, "Failed to update account" +e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });*/
       /* final DocumentReference docref = fStore.collection("Utenti").document(friendId);

        fStore.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(docref);

                        // Note: this could be done without a transaction
                        //       by updating the population using FieldValue.increment()
                        //double newPopulation = snapshot.getDouble("population") + 1;
                        //transaction.update(sfDocRef, "population", newPopulation);
                        transaction.update(docref, "name", friendName);
                        transaction.update(docref, "surname", friendSurname);
                        transaction.update(docref, "email", emailFriend);
                        transaction.update(docref, "points", friendActualPoints);
                        transaction.update(docref, "address", friendAddress);
                        transaction.update(docref, "phoneNumber", friendPhoneNumber);
                        //transaction.update(sfDocRef, "rating", friendRating);


                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Transaction success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Transaction failure.", e);
                    }
                });
*/



    }

}