package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MyProfile extends AppCompatActivity {

    ImageView menu, modifyAccount;
    Button mybooked, myfeedback, donation;
    TextView name, surname, phonenumber, email, rating, points, address;

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

        //FirebaseDatabase databaseProfile = FirebaseDatabase.getInstance();

        name = findViewById(R.id.TextViewName);
        surname = findViewById(R.id.TextViewLastname);
        phonenumber = findViewById(R.id.tv_phoneNumber);
        email = findViewById(R.id.tv_email);
        rating = findViewById(R.id.tv_rating);
        points = findViewById(R.id.tv_points);
        address = findViewById(R.id.tv_address);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // -------------- DOBBIAMO VEDERE ----------------
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // ------------------------------------------------

        String Name = name.getText().toString().trim();
        String Surname = surname.getText().toString().trim();
        String Phone = phonenumber.getText().toString().trim();
        String Rating = rating.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Points = points.getText().toString().trim();
        String Address = address.getText().toString().trim();

        Users user = new Users(Surname, Email, Address, Name, Integer.parseInt(Points), Phone);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore ref = FirebaseFirestore.getInstance();
        CollectionReference usersRef = ref.collection("Utenti");
        usersRef.document(uid).set(user);

        db.collection("Utenti").document(uid).set(user);

        name.setText(user.getName());
        surname.setText(user.getSurname());
        phonenumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        points.setText(String.valueOf(user.getPoints()));
        address.setText(user.getAddress());
        rating.setText("0");

    }












}