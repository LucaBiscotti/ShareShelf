package com.example.shareshelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class OtherProfile extends AppCompatActivity {

    ImageView arrow;
    TextView viewName, viewLastname, viewPhoneNumber, viewEmail, viewAddress, viewPoints;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_donation);


        arrow = findViewById(R.id.go_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherProfile.this, ListCandidate.class);
                startActivity(intent);
            }
        });

        viewName = findViewById(R.id.TextViewName);
        viewLastname = findViewById(R.id.TextViewLastname);
        viewPhoneNumber = findViewById(R.id.tv_phoneNumber);
        viewEmail = findViewById(R.id.tv_email);
        viewPoints = findViewById(R.id.tv_rating);
        viewAddress = findViewById(R.id.tv_points);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        DocumentReference documentReference = fStore.document(userId);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                        viewName.setText(val.toString());
                    }
                    if(user.containsKey("surname")){
                        Object val = user.get("surname");
                        viewLastname.setText(val.toString());
                    }
                    if(user.containsKey("email")){
                        Object val = user.get("email");
                        viewEmail.setText(val.toString());
                    }
                    if(user.containsKey("points")){
                        Object val = user.get("points");
                        viewPoints.setText(val.toString());
                    }
                    if(user.containsKey("address")){
                        Object val = user.get("address");
                        viewAddress.setText(val.toString());
                    }
                    if(user.containsKey("phoneNumber")) {
                        Object val = user.get("phoneNumber");
                        viewPhoneNumber.setText(val.toString());
                    }
                }
            }
        });
    }
}
