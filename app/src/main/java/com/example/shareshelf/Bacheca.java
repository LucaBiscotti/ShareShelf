package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bacheca extends AppCompatActivity {

    private RecyclerView courseRV;
    private ArrayList<Noticeboard> dataNoticeArrayList;
    private AdapterCard dataRVAdapter;
    private DatabaseReference db;

    ImageView menu, filter;
    View addNotice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacheca);

        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, Menu.class);
                startActivity(intent);
            }
        });

        filter = findViewById(R.id.goToFilter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, Filter.class);
                startActivity(intent);
            }
        });

        addNotice = findViewById(R.id.btnAdd);
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bacheca.this, CreateNoticeboard.class);
                startActivity(intent);
            }
        });

        // initializing our variables.
        courseRV = findViewById(R.id.idRVItems);
        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseDatabase.getInstance().getReference();
        Query query = db.child("Annunci");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("DataSnapshot", "Data retrieved from the database");
                } else {
                    Log.d("DataSnapshot", "No data retrieved from the database");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DataSnapshot", "Retrieving data failed: " + databaseError.getMessage());
            }
        });


        // adding horizontal layout manager for our recycler view.
        courseRV.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Noticeboard> options
                = new FirebaseRecyclerOptions.Builder<Noticeboard>()
                .setQuery(query, Noticeboard.class)
                .build();

        // adding our array list to our recycler view adapter class.
        dataRVAdapter = new AdapterCard(options);

        // creating our new array list
        //dataNoticeArrayList = new ArrayList<>();

        //loadrecyclerViewData();

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);

        //courseRV.setHasFixedSize(true);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        dataRVAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        dataRVAdapter.stopListening();
    }

}