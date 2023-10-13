package com.ispc.gymapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;

public class FireStoreExerciseAdapter extends FirestoreRecyclerAdapter<Exercise, FireStoreExerciseAdapter.ExerciseViewHolder> {

    public FireStoreExerciseAdapter(@NonNull FirestoreRecyclerOptions<Exercise> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FireStoreExerciseAdapter.ExerciseViewHolder holder, int position, @NonNull Exercise model) {
        String title = model.getTitle();
        holder.textView.setText(title);

        if (title.contains("Abdominales")) {
            holder.textView.setBackgroundResource(R.drawable.abs);
        } else if (title.contains("Pecho")) {
            holder.textView.setBackgroundResource(R.drawable.chest);
        } else if (title.contains("Brazo")) {
            holder.textView.setBackgroundResource(R.drawable.arm);
        } else if (title.contains("Pierna")) {
            holder.textView.setBackgroundResource(R.drawable.leg);
        } else if (title.contains("Espalda")){
            holder.textView.setBackgroundResource(R.drawable.back);
        }
    }

    @NonNull
    @Override
    public FireStoreExerciseAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exerciselist_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.exerciseItem);
        }
    }
}
