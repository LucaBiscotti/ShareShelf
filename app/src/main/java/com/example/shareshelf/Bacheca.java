package com.example.shareshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class Bacheca extends AppCompatActivity {
    private FirestoreRecyclerAdapter<Noticeboard, AdapterCard.ViewHolder> dataRVAdapter;
    ImageView menu, filter;
    View addNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

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
        Query query = FirebaseFirestore.getInstance()
                .collection("Annunci");

        FirestoreRecyclerOptions<Noticeboard> options =
                new FirestoreRecyclerOptions.Builder<Noticeboard>()
                .setQuery(query, Noticeboard.class)
                .build();

        dataRVAdapter = new AdapterCard(options);

        // adding horizontal layout manager for our recycler view.
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);
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