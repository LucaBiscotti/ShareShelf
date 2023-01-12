package com.example.shareshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareshelf.Noticeboard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class AdapterCard extends FirebaseRecyclerAdapter<Noticeboard, AdapterCard.ViewHolder> {
    private ArrayList<Noticeboard> dataModalArrayList;
    private Context context;

    // constructor class for our Adapter
    public AdapterCard(@NonNull FirebaseRecyclerOptions<Noticeboard> options) {
        super(options);
    }

    @NonNull
    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new AdapterCard.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_service_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCard.ViewHolder holder, int position, @NonNull Noticeboard model) {
        // setting data to our views in Recycler view items.
        holder.category.setText(model.getCategory());
        holder.dateStart.setText((CharSequence) model.getDateStart());
        holder.description.setText(model.getDescription());
        holder.duration.setText(model.getDuration());
        holder.idCreator.setText(model.getIDCreator());
        holder.title.setText(model.getTitle());
        holder.type.setText(model.getType());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our
        // views of recycler items.
        private TextView category;
        private TextView dateStart;
        private TextView description;
        private TextView duration;
        private TextView idCreator;
        private TextView title;
        private TextView type;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing the views of recycler views.
            category = itemView.findViewById(R.id.title);
            dateStart = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.title);
            idCreator = itemView.findViewById(R.id.title);
            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.title);
        }
    }
}