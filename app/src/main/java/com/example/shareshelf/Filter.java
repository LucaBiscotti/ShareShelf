package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class Filter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView menu;
    RadioGroup type;
    Spinner spinner;
    CalendarView date;
    Switch dateSwitch;
    Button save, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Menu.class);
                startActivity(intent);
            }
        });

        spinner = findViewById(R.id.spinner_categories);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Filter.this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        date= findViewById(R.id.calendarView);

        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = "Hai selezionato il giorno: " + i2 + " mese : " + (i1 + 1) + " anno " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


            }
        });

        dateSwitch = findViewById(R.id.dateSwitch);
        dateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateSwitch.isChecked())
                    date.setVisibility(view.VISIBLE);
                else
                    date.setVisibility(view.GONE);
            }
        });


        save= findViewById(R.id.btnFilterSave);
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                type = findViewById(R.id.radioGroup2);

                spinner.setOnItemSelectedListener(Filter.this);
                String category = spinner.getSelectedItem().toString();

                int selectedId = type.getCheckedRadioButtonId();

                date = findViewById(R.id.calendarView);

                Intent intent = new Intent(Filter.this, Bacheca.class);
                intent.putExtra("type","" +selectedId);
                if(dateSwitch.isChecked())
                    intent.putExtra("date","" + new Date(date.getDate()));
                if(!category.equals("Tutti"))
                    intent.putExtra("category", category);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.btnFilterBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Filter.this, Bacheca.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Filter.this, Bacheca.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.getItemAtPosition(0);
    }
}