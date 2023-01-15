package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {

    ImageView menu, modifyAccount;
    Button mybooked, donation;
    TextView name, surname, phonenumber, email, points, address;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String myPoints, myEmail;

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



        modifyAccount = findViewById(R.id.btnModifyAccount);
        modifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, ModifyProfile.class);
                startActivity(intent);
            }
        });

        name = findViewById(R.id.TextViewName);
        surname = findViewById(R.id.TextViewLastname);
        phonenumber = findViewById(R.id.tv_phoneNumber);
        email = findViewById(R.id.tv_email);
        points = findViewById(R.id.tv_points);
        address = findViewById(R.id.tv_address);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();



        fStore.collection("Utenti").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                    Map<String, Object> user = new HashMap<>();
                    user = task.getResult().getData();

                    if(user.containsKey("name")){
                        Object val = user.get("name");
                        name.setText(val.toString());
                    }
                    if(user.containsKey("surname")){
                        Object val = user.get("surname");
                        surname.setText(val.toString());
                    }
                    if(user.containsKey("email")){
                        Object val = user.get("email");
                        email.setText(val.toString());
                        myEmail = val.toString();
                    }
                    if(user.containsKey("points")){
                        Object val = user.get("points");
                        myPoints = val.toString();
                        points.setText(val.toString());
                    }
                    if(user.containsKey("address")){
                        Object val = user.get("address");
                        address.setText(val.toString());
                    }
                    if(user.containsKey("phoneNumber")){
                        Object val = user.get("phoneNumber");
                        phonenumber.setText(val.toString());
                    }

                }
            }
        });

        donation= findViewById(R.id.btn_toDonation);
        donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfile.this, PointsDonation.class);
                //startActivity(intent);
                //Intent intent = new Intent(view.getContext(), NoticeboardDetails.class);
                intent.putExtra("id", userId);
                intent.putExtra("mypoints", myPoints);
                intent.putExtra("email", myEmail);
                startActivity(intent);
            }
        });



    }




}