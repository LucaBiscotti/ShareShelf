package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoticeboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageView backArrow;
    EditText et_dateStart, et_h, et_min, et_title, et_descriprion;
    String category, description, idCreator, type, title, state;
    String date, str_h, str_min;
    Integer duration, h, min;
    Date dateStart;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    RadioGroup radioGroup;
    Button save, cancel;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_noticeboard);

        backArrow = findViewById(R.id.go_back);
        cancel = findViewById(R.id.button);
        save = findViewById(R.id.btnSave);
        et_dateStart = findViewById(R.id.inputDate);
        et_h = findViewById(R.id.inputHour);
        et_min = findViewById(R.id.inputMin);
        et_title = findViewById(R.id.input_title);
        et_descriprion = findViewById(R.id.inputText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        spinner = (Spinner) findViewById(R.id.spinner_categories);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        idCreator = fAuth.getCurrentUser().getUid();
        DocumentReference df = fStore.collection("Utenti").document(idCreator);

        radioGroup.clearCheck();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateNoticeboard.this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNoticeboard.this, Bacheca.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNoticeboard.this, Bacheca.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(CreateNoticeboard.this, "No answer has been selected", Toast.LENGTH_SHORT).show();
                }
                else {

                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);

                    // Now display the value of selected item
                    // by the Toast message
                    Toast.makeText(CreateNoticeboard.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                    type = String.valueOf(radioButton.getText());
                }

                title = et_title.getText().toString();
                description = et_descriprion.getText().toString();
                date = et_dateStart.getText().toString();
                if(et_h.getText().toString().isEmpty()){
                    h = 0;
                }
                if(et_min.getText().toString().isEmpty()){
                    min = 0;
                }else {
                    h = Integer.parseInt(et_h.getText().toString());
                    min = Integer.parseInt(et_min.getText().toString());
                }
                duration = h*60 + min;

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
                try {
                    dateStart = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                spinner.setOnItemSelectedListener(CreateNoticeboard.this);
                category = spinner.getSelectedItem().toString();


                state = "In attesa di prenotazioni";
                Noticeboard noticeboard = new Noticeboard(category, dateStart, description, duration.longValue(), df, type, title, state);
                fStore.collection("Annunci").document()
                        .set(noticeboard)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully written!");
                                Intent intent = new Intent( CreateNoticeboard.this, Bacheca.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error writing document", e);
                            }
                        });


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}