package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class ModifyProfile extends AppCompatActivity {

    EditText inputName, inputLastName, inputPhoneNumber, inputEmail, inputAddress;
    Button save, cancel;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = users.getUid();
        documentReference = db.collection("Utenti").document(currentuid);

        save = findViewById(R.id.btn_saveModify);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputName = findViewById(R.id.et_myName);
                inputLastName = findViewById(R.id.et_myLastname);
                inputEmail = findViewById(R.id.et_myEmail);
                inputPhoneNumber = findViewById(R.id.et_myPhoneNumber);
                inputAddress = findViewById(R.id.et_myAddress);

                final String email = inputEmail.getText().toString();
                users.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        String name = inputName.getText().toString().trim();
                        String lastname = inputLastName.getText().toString().trim();
                        String email = inputEmail.getText().toString().trim();
                        String phonenumber = inputPhoneNumber.getText().toString().trim();
                        String address = inputAddress.getText().toString().trim();

                        Map<String, Object> edited = new HashMap<>();

                        if(inputName != null)
                            edited.put("name", name);
                        if(inputLastName != null)
                            edited.put("surname", lastname);
                        if(inputEmail != null)
                            edited.put("email", email);
                        if(inputPhoneNumber != null)
                            edited.put("phoneNumber", phonenumber);
                        if(inputAddress != null)
                            edited.put("address", address);
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ModifyProfile.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                sendUsertoNextActivity();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModifyProfile.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        cancel = findViewById(R.id.btnCancelModify);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyProfile.this, MyProfile.class);
                startActivity(intent);
            }
        });
    }




    private void sendUsertoNextActivity() {
        Intent intent = new Intent(ModifyProfile.this, MyProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}