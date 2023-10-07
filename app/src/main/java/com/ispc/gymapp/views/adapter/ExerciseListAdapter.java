package com.ispc.gymapp.views.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;

import java.util.ArrayList;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    Context context;
    private final ArrayList<Exercise> exercises;


    public ExerciseListAdapter(Context context, ArrayList<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = LayoutInflater.from(context).inflate(R.layout.exerciselist_item, parent, false);
        return new ExerciseViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);
        String title = currentExercise.getTitle();
        holder.textViewItem.setText(currentExercise.getTitle());

        if (title.contains("Abdominales")) {
            holder.textViewItem.setBackgroundResource(R.drawable.abs);
        } else if (title.contains("Pecho")) {
            holder.textViewItem.setBackgroundResource(R.drawable.chest);
        } else if (title.contains("Brazo")) {
            holder.textViewItem.setBackgroundResource(R.drawable.arm);
        } else if (title.contains("Pierna")) {
            holder.textViewItem.setBackgroundResource(R.drawable.leg);
        } else if (title.contains("Espalda")){
            holder.textViewItem.setBackgroundResource(R.drawable.back);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        public final TextView textViewItem;
        final ExerciseListAdapter exerciseListAdapter;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseListAdapter exerciseListAdapter) {
            super(itemView);
            this.textViewItem = itemView.findViewById(R.id.exerciseItem);
            this.exerciseListAdapter = exerciseListAdapter;
        }
    }
}
