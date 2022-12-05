package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateUser extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText inputEmail, inputPassword, inputConfirmPsw, inputName, inputSurname, inputPhoneNumber;
    Button btnNextPageRegistration;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    //FirebaseAuth mAuth;
    //FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

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
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmpassword=inputPassword.getText().toString();
        String name=inputName.getText().toString();
        String surname=inputSurname.getText().toString();
        String phone=inputPhoneNumber.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct email");
        } else if (name.isEmpty()) {
            inputName.setError("Inserisci un nome");
        }else if (surname.isEmpty()) {
            inputSurname.setError("Inserisci un cognome");
        }else if (phone.isEmpty() || phone.length()!=10) {
            inputPhoneNumber.setError("Inserisci un numero di telefono reale");
        }else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter propper password");
        } else if (!password.equals(confirmpassword)) {
            inputConfirmPsw.setError("Password not match");
        } else {
            progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

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

    private void sendUsertoNextActivity() {
        Intent intent = new Intent(CreateUser.this, LocationUser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}