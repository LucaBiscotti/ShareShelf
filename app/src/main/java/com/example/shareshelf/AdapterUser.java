package com.example.shareshelf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;


public class AdapterUser extends FirestoreRecyclerAdapter<Booking, AdapterUser.ViewHolder>{
    // constructor class for our Adapter
    FirebaseFirestore fStore;
    Context context;
    public AdapterUser(@NonNull FirestoreRecyclerOptions<Booking> options) {
        super(options);
    }

    @NonNull
    @Override
    public AdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdapterUser.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_line_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.ViewHolder holder, int position, @NonNull Booking model) {
        // setting data to our views in Recycler view items.
        fStore.getInstance();
        String name, surname, email, address, phoneNumber;
        DocumentReference df = model.getIdCandidate();
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getData()));
                    Map<String, Object> users = new HashMap<>();
                    users = task.getResult().getData();

                    if (users.containsKey("email")) {
                        Object val = users.get("email");
                        holder.tv_email.setText(val.toString());
                    }
                }
            }
        });
        DocumentReference doc = model.getIdNoticeboard();
        context = holder.btn_accept_candidate.getContext();

        holder.btn_accept_candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query colRef = fStore.collection("Prenotazioni");
                colRef.whereArrayContains("idAnnuncio", doc).whereNotEqualTo("idCandidato", df);

                // Get the query snapshot
                colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Create a batch write
                            WriteBatch batch = fStore.batch();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Add each document to the batch write
                                batch.delete(document.getReference());
                            }
                            // Commit the batch write
                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "Documents deleted successfully");
                                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(context, MyNoticeboard.class);
                                        context.startActivity(i);
                                    } else {
                                        Log.w("TAG", "Error deleting documents", task.getException());
                                        Toast.makeText(context, "Error deleting", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Log.w("TAG", "Error getting documents", task.getException());
                        }
                    }
                });

                Query query = fStore.collection("Prenotazioni");
                query.whereArrayContains("idAnnuncio", doc).whereEqualTo("idCandidato", df);

                // Get the query snapshot
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentToChangeId = document.getId();
                                final DocumentReference sfDocRef = fStore.collection("Annunci").document(documentToChangeId);
                                // Update one field, creating the document if it does not already exist.
                                Map<String, Object> data = new HashMap<>();
                                data.put("state", "Accettato");

                                sfDocRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("TAG", "DocumentSnapshot successfully updated!");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error updating document", e);
                                    }
                                });

                            }
                        }
                    }
                });

            }
        });

        //context = holder.btn_visit_profile.getContext();

        /*holder.btn_visit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Visit profile", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, OtherProfile.class);
                i.putExtra("id", df.toString());
                context.startActivity(i);
            }
        });*/




    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our
        // views of recycler items.
        private final TextView tv_email;
        private final Button btn_accept_candidate, btn_visit_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_email = itemView.findViewById(R.id.tv_email_card);
            btn_accept_candidate = itemView.findViewById(R.id.btn_accept_candidate);
            btn_visit_profile = itemView.findViewById(R.id.btn_visit_profile);

            btn_visit_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }



    }



}
