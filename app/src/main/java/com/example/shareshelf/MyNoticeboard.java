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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyNoticeboard extends AppCompatActivity {

    ImageView menu;
    private FirestoreRecyclerAdapter<Noticeboard, AdapterCard.ViewHolder> dataRVAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private AdapterCard.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_noticeboard);
        listener = new AdapterCard.RecyclerViewClickListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                Noticeboard model = documentSnapshot.toObject(Noticeboard.class);
                Intent intent = new Intent(MyNoticeboard.this, DetailsMyNoticeboard.class);
                intent.putExtra("Titolo",model.getTitolo());
                intent.putExtra("Tipo",model.getTipo());
                intent.putExtra("Categoria",model.getCategoria());
                intent.putExtra("Data","" + model.getDataInizio());
                intent.putExtra("Durata","" + model.getDurata());
                intent.putExtra("Creatore", model.getIDCreatore().toString());
                intent.putExtra("Stato",model.getStato());
                intent.putExtra("Descrizione",model.getDescrizione());
                startActivity(intent);
            }
        };


        putQuery(true);


        menu = findViewById(R.id.btnMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyNoticeboard.this, Menu.class);
                startActivity(intent);
            }
        });
    }

    private void putQuery(boolean b) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        RecyclerView courseRV = findViewById(R.id.rv_my_noticeboard);

        DocumentReference ref = fStore.collection("Utenti").document(userId);
        Query q;

        q = FirebaseFirestore.getInstance()
                .collection("Annunci")
                .whereEqualTo("idcreatore", ref);


        FirestoreRecyclerOptions<Noticeboard> options =
                new FirestoreRecyclerOptions.Builder<Noticeboard>()
                        .setQuery(q, Noticeboard.class)
                        .build();
        dataRVAdapter = new AdapterCard(options,listener);
        // adding horizontal layout manager for our recycler view.
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);
    }

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
