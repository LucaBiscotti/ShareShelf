package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class CreateUser extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText inputEmail, inputPassword, inputConfirmPsw, inputName, inputSurname, inputPhoneNumber, inputAddress;
    Button btnNextPageRegistration;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private String email, password1, password2, name, lastname, phoneNumber, address;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        db = FirebaseFirestore.getInstance();

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
        email=inputEmail.getText().toString();
        password1=inputPassword.getText().toString();
        password2=inputPassword.getText().toString();
        name=inputName.getText().toString();
        lastname =inputSurname.getText().toString();
        phoneNumber=inputPhoneNumber.getText().toString();
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
        CollectionReference dbUsers = db.collection("Utenti");
        UUID uuidObj = UUID.randomUUID();
        Users user = new Users(String.valueOf(uuidObj), lastname, email, address, name, password1, 0, phoneNumber);


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
        });
    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(CreateUser.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}