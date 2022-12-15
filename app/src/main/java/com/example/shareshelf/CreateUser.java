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

public class CreateUser extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText inputEmail, inputPassword, inputConfirmPsw, inputName, inputSurname, inputPhoneNumber;
    Button btnNextPageRegistration;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    private String email, password1, password2, name, lastname, phoneNumber;

    private FirebaseFirestore db;

    //FirebaseAuth mAuth;
    //FirebaseUser mUser;

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
        inputConfirmPsw=findViewById(R.id.inputPwsConfirmReg);
        btnNextPageRegistration=findViewById(R.id.btnNextPageReg);
        progressDialog=new ProgressDialog(this);
        //mAuth=FirebaseAuth.getInstance();
        //mUser=mAuth.getCurrentUser();

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
            /*progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/

            addDataToFirestore(name, lastname, email, password1);

            /*mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUsertoNextActivity();
                        Toast.makeText(CreateUser.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(CreateUser.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

    private void addDataToFirestore(String name, String lastname, String email, String password1) {
        CollectionReference dbUsers = db.collection("Utenti");
        // String Id, String Surname, String Email, String Address, String Name, String Password, Integer Points, String PhoneNumber
        Users user = new Users("1", lastname, email, "", name, password1, 0, phoneNumber);

        dbUsers.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateUser.this, "Your user has been added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateUser.this, "Fail to add user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(CreateUser.this, LocationUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}