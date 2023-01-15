package com.example.shareshelf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterCard extends FirestoreRecyclerAdapter<Noticeboard, AdapterCard.ViewHolder> {
    private static Context context;
    private RecyclerViewClickListener listener;

    // constructor class for our Adapter
    public AdapterCard(@NonNull FirestoreRecyclerOptions<Noticeboard> options, RecyclerViewClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdapterCard.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_service_card, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterCard.ViewHolder holder, int position, @NonNull Noticeboard model) {
        // setting data to our views in Recycler view items.
        holder.category.setText(model.getCategoria());
        holder.dateStart.setText(model.getDataInizio().toString());
        holder.description.setText(model.getDescrizione());
        holder.duration.setText(model.getDurata().toString());
        holder.idCreator.setText(model.getIDCreatore().toString());
        holder.title.setText(model.getTitolo());
        holder.type.setText(model.getTipo());
        holder.state.setText(model.getStato());

    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // creating variables for our
        // views of recycler items.
        private final TextView category;
        private final TextView dateStart;
        private final TextView description;
        private final TextView duration;
        private final TextView idCreator;
        private final TextView title;
        private final TextView type;
        private final TextView state;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            // initializing the views of recycler views.
            category = itemView.findViewById(R.id.textViewCategory);
            dateStart = itemView.findViewById(R.id.dateStart);
            description = itemView.findViewById(R.id.textViewDescription);
            duration = itemView.findViewById(R.id.textViewDuration);
            idCreator = itemView.findViewById(R.id.idCreator);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
            state = itemView.findViewById(R.id.stato);
        }


        @Override
        public void onClick(View view) {
            listener.onClick(view,getBindingAdapterPosition());
        }
    }
}