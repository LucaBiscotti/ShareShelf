package com.example.shareshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListCandidate extends AppCompatActivity {
    private FirestoreRecyclerAdapter<Booking, AdapterUser.ViewHolder> dataRVAdapter;
    ImageView back;
    View addNotice;
    String id;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_candidate);

        back = findViewById(R.id.backArrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListCandidate.this, MyNoticeboard.class);
                startActivity(intent);
            }
        });

        // initializing our variables.
        RecyclerView courseRV = findViewById(R.id.rv_list_candidate);
        // initializing our variable for firebase
        // firestore and getting its instance.

        Intent data = getIntent();
        id=data.getStringExtra("id");

        DocumentReference df = fStore.collection("Annuncio").document(id);

        /*CollectionReference bookingRef = fStore.collection("Prenotazioni");
        Query query1 = bookingRef.whereEqualTo("idAnnuncio", df);*/
        // ArrayList<DocumentReference> coll = new ArrayList<>();
        // CollectionReference citiesRef = fStore.collection("Prenotazioni");
        //citiesRef.whereArrayContains("idAnnuncio", df);
      /*  citiesRef.whereArrayContains("idAnnuncio", df).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idCreator = document.getString("idCreator");
                        DocumentReference doc1 = fStore.collection("Utenti").document(idCreator);
                        coll.add(doc1);

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });*/

        Query query = fStore.collection("Prenotazioni").whereArrayContains("idAnnuncio", df);

     /*   for(DocumentReference doc2 : coll){

        }*/


        /*Query query = FirebaseFirestore.getInstance()
                .collection("Utenti");

        FirestoreRecyclerOptions<Users> options =
                new FirestoreRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();*/
        FirestoreRecyclerOptions<Booking> options = new FirestoreRecyclerOptions.Builder<Booking>().setQuery(query, Booking.class).build();

        //new FirestoreRecyclerOptions.Builder<Users>().setSnapshotArray(coll).build();

        dataRVAdapter = new AdapterUser(options);

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