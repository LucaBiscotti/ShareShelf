package com.example.shareshelf;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateUser extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText inputEmail, inputPassword, inputConfirmPsw, inputName, inputSurname, inputPhoneNumber, inputAddress;
    Button btnNextPageRegistration;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private String email, password1, password2, name, lastname, phoneNumber, address;

    private FirebaseFirestore db;
    FirebaseAuth fAuth;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputName=findViewById(R.id.inputName);
        inputSurname=findViewById(R.id.inputSurname);
        inputEmail=findViewById(R.id.inputEmailRegistration);
        inputPhoneNumber=findViewById(R.id.inputPhone);
        inputPassword=findViewById(R.id.inputPsw);
        inputConfirmPsw=findViewById(R.id.inputPwsConfirmReg2);
        inputAddress = findViewById(R.id.inputAddressUser);
        btnNextPageRegistration=findViewById(R.id.btnNextPageReg);


        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateUser.this, Login.class));
            }
        });

        btnNextPageRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        email=inputEmail.getText().toString().trim();
        password1=inputPassword.getText().toString();
        password2=inputPassword.getText().toString();
        name=inputName.getText().toString().trim();
        lastname =inputSurname.getText().toString().trim();
        phoneNumber=inputPhoneNumber.getText().toString().trim();
        address=inputAddress.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct email");
        } else if (name.isEmpty()) {
            inputName.setError("Inserisci un nome");
        }else if (lastname.isEmpty()) {
            inputSurname.setError("Inserisci un cognome");
        }else if (phoneNumber.isEmpty() || phoneNumber.length()!=10) {
            inputPhoneNumber.setError("Inserisci un numero di telefono reale");
        }else if (password1.isEmpty() || password1.length() < 6) {
            inputPassword.setError("Enter propper password");
        } else if (!password1.equals(password2)) {
            inputConfirmPsw.setError("Password not match");
        } else {

            addDataToFirestore(name, lastname, email, password1, address);

        }
    }

    private void addDataToFirestore(String name, String lastname, String email, String password1, String address) {

      //  UUID uuidObj = UUID.randomUUID();
        /*CollectionReference dbUsers = db.collection("Utenti");

        Users user = new Users(lastname, email, address, name, password1, 0, phoneNumber);


        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateUser.this, "Your user has been added", Toast.LENGTH_SHORT).show();
                sendUsertoNextActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateUser.this, "Fail to add user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });*/

        fAuth.createUserWithEmailAndPassword(email, password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser userId = fAuth.getCurrentUser();
                Toast.makeText(CreateUser.this, "Account created", Toast.LENGTH_SHORT).show();
                DocumentReference df = db.collection("Utenti").document(userId.getUid());
                Users user = new Users(lastname, email, address, name, password1, 0, phoneNumber);
                //df.set(user);
                df.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Inserito con successo" + userId);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "On failure" + e.toString());
                    }
                });

                sendUsertoNextActivity();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateUser.this, "Failed to create account" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(CreateUser.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}