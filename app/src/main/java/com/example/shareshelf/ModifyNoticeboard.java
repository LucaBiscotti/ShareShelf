package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ModifyNoticeboard extends AppCompatActivity {
    Date date;
    Integer duration, min, h;
    String str_date, str_duration, str_h, str_min, description, id;
    EditText et_h, et_min, et_description, et_date;
    Button modify, cancel;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_noticeboard);
        Intent intent = getIntent();
        str_date = intent.getStringExtra("date");
        str_duration = intent.getStringExtra("duration");
        description = intent.getStringExtra("description");
        id = intent.getStringExtra("id");

        fStore = FirebaseFirestore.getInstance();

        duration = Integer.parseInt(str_duration);
        h = duration/60;
        min = duration-h*60;
        str_h = String.valueOf(h);
        str_min = String.valueOf(min);


        et_h = findViewById(R.id.inputHour2);
        et_min = findViewById(R.id.inputMin2);
        et_description = findViewById(R.id.inputText2);
        et_date = findViewById(R.id.inputDate2);

        et_h.setText(str_h);
        et_min.setText(str_min);
        et_description.setText(description);
        et_date.setText(str_date);

        modify= findViewById(R.id.btn_saveModify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Update one field, creating the document if it does not already exist.

                str_h = et_h.getText().toString().trim();
                str_min = et_min.getText().toString().trim();
                h = Integer.parseInt(str_h);
                min = Integer.parseInt(str_min);
                duration = min + h*60;
                str_date = et_date.getText().toString().trim();
                description = et_description.getText().toString().trim();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
                try {
                    date = formatter.parse(str_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Map<String, Object> edited = new HashMap<>();

                if(duration != null)
                    edited.put("durata", duration);
                if(str_date != null)
                    edited.put("dataInizio", date);
                if(description != null)
                    edited.put("descrizione", description);

                DocumentReference df = fStore.collection("Annunci").document(id);
                // ATTENZIONE!!!
                // potrebbe essere da cambiare l'id da string in document reference.
                // CIAO LORENZO

                df
                        .update(edited)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                Toast.makeText(ModifyNoticeboard.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ModifyNoticeboard.this, MyNoticeboard.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                                Toast.makeText(ModifyNoticeboard.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        cancel= findViewById(R.id.btnCancelModify);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyNoticeboard.this, MyNoticeboard.class);
                startActivity(intent);
            }
        });


    }

}