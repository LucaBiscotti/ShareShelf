package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Bacheca extends AppCompatActivity {
    private FirestoreRecyclerAdapter<Noticeboard, AdapterCard.ViewHolder> dataRVAdapter;
    ImageView menu, filter;
    View addNotice;
    private AdapterCard.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        listener = new AdapterCard.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.d("Messaggio_test", "aaaaaaaa");
                Intent intent = new Intent(Bacheca.this, BookedNoticeboard.class);
                startActivity(intent);
            }
        };

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(view -> {
            Intent intent = new Intent(Bacheca.this, Menu.class);
            startActivity(intent);
        });

        filter = findViewById(R.id.goToFilter);
        filter.setOnClickListener(view -> {
            Intent intent = new Intent(Bacheca.this, Filter.class);
            startActivity(intent);
        });

        addNotice = findViewById(R.id.btnAdd);
        addNotice.setOnClickListener(view -> {
            Intent intent = new Intent(Bacheca.this, CreateNoticeboard.class);
            startActivity(intent);
        });

        // initializing our variables.
        RecyclerView courseRV = findViewById(R.id.rv_noticeboard_bacheca);
        // initializing our variable for firebase
        // firestore and getting its instance.

        String type = getIntent().getStringExtra("type");
        String date = getIntent().getStringExtra("date");
        String category = getIntent().getStringExtra("category");

        FirestoreRecyclerOptions<Noticeboard> options = applyFilter(type,date,category);

        dataRVAdapter = new AdapterCard(options, listener);

        // adding horizontal layout manager for our recycler view.
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);

    }

    public FirestoreRecyclerOptions<Noticeboard> applyFilter(String type, String date, String category){
        Query q;

        if(type == null || type.equals("-1")) {
            Log.d("Messaggio_test", "tipo no");
            if(date == null){
                Log.d("Messaggio_test", "tipo data no");
                if(category == null){
                    Log.d("Messaggio_test", "tipo categoria no");
                    q = noFilters();
                }
                else {
                    Log.d("Messaggio_test", "categoria si " + category);
                    q = categoryFilter(category);
                }
            }
            else{
                if(category == null){
                    q = dateFilter(date);
                }
                else {
                    q = categoryDateFilter(category,date);
                }
            }
        }
        else{
            Log.d("Messaggio_test", "tipo si " + type);
            int i = Integer.parseInt(type);
            if(date == null){
                if(category == null){
                    q = typeFilter(i);
                }
                else {
                    Log.d("Messaggio_test", "tipo categoria si " + category);
                    q = typeCategoryFilter(i,category);
                }
            }
            else{
                if(category == null){
                    Log.d("Messaggio_test", "data son qua ");
                    q = typeDateFilter(i,date);
                }
                else {
                    Log.d("Messaggio_test", "tutto " + date + " " + category);
                    q = allFilter(i,category,date);
                }
            }
        }

        FirestoreRecyclerOptions<Noticeboard> options =
                new FirestoreRecyclerOptions.Builder<Noticeboard>()
                        .setQuery(q, Noticeboard.class)
                        .build();
        return options;
    }

    private Query noFilters(){
        return FirebaseFirestore.getInstance()
                .collection("Annunci")
                .whereEqualTo("stato", "In attesa di prenotazioni");
    }

    private Query categoryFilter(String category){
        return FirebaseFirestore.getInstance()
                .collection("Annunci")
                .whereEqualTo("categoria", category)
                .whereEqualTo("stato", "In attesa di prenotazioni");
    }

    private Query dateFilter(String timestamp){
        return FirebaseFirestore.getInstance()
                .collection("Annunci")
                .whereEqualTo("dataInizio", timestamp)
                .whereEqualTo("stato", "In attesa di prenotazioni");
    }

    private Query typeFilter(int i){
        switch (i) {
            case R.id.btnOffroFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "offro")
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            case R.id.btnCercoFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "cerco")
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            default:
                return noFilters();
        }
    }

    private Query categoryDateFilter(String category, String timestamp){
        return FirebaseFirestore.getInstance()
                .collection("Annunci")
                .whereEqualTo("categoria", category)
                .whereEqualTo("dataInizio", timestamp)
                .whereEqualTo("stato", "In attesa di prenotazioni");
    }

    private Query typeCategoryFilter(int i, String category) {
        switch (i) {
            case R.id.btnOffroFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "offro")
                        .whereEqualTo("categoria", category)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            case R.id.btnCercoFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "cerco")
                        .whereEqualTo("categoria", category)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            default:
                return categoryFilter(category);
        }
    }

    private Query typeDateFilter(int i, String timestamp) {
        switch (i) {
            case R.id.btnOffroFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "offro")
                        .whereEqualTo("dataInizio", timestamp)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            case R.id.btnCercoFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "cerco")
                        .whereEqualTo("dataInizio", timestamp)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            default:
                return dateFilter(timestamp);
        }
    }

    private Query allFilter(int i, String category, String timestamp) {
        switch (i) {
            case R.id.btnOffroFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "offro")
                        .whereEqualTo("dataInizio", timestamp)
                        .whereEqualTo("categoria", category)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            case R.id.btnCercoFilter:
                return FirebaseFirestore.getInstance()
                        .collection("Annunci")
                        .whereEqualTo("tipo", "cerco")
                        .whereEqualTo("dataInizio", timestamp)
                        .whereEqualTo("categoria", category)
                        .whereEqualTo("stato", "In attesa di prenotazioni");
            default:
                return categoryDateFilter(category,timestamp);
        }
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    protected void onStart() {
        super.onStart();
        dataRVAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    protected void onStop() {
        super.onStop();
        dataRVAdapter.stopListening();
    }

}