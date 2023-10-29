package com.ispc.gymapp.views.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;

import java.util.ArrayList;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    Context context;
    private final ArrayList<Exercise> exercises;
    private ArrayList<String> favoriteExerciseTitles = new ArrayList<>();


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

        boolean esFavorito = favoriteExerciseTitles.contains(title);
        if (esFavorito) {
            holder.favoriteButton.setImageResource(R.drawable.heart_fill);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.heart);
        }

        // Manejador de clics para el botón de favoritos
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Exercise exercise = exercises.get(position);
                    if (favoriteClickListener != null) {
                        favoriteClickListener.onFavoriteClick(position);
                    }
                }
                if (esFavorito) {
                    quitarEjercicioDeFavoritos(title);
                    holder.favoriteButton.setImageResource(R.drawable.heart);
                } else {
                    agregarEjercicioAFavoritos(title);
                    holder.favoriteButton.setImageResource(R.drawable.heart_fill);
                }
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favoriteExerciseTitles", new HashSet<>(favoriteExerciseTitles));
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        public final TextView textViewItem;
        final ExerciseListAdapter exerciseListAdapter;
        public final ImageButton favoriteButton;

        public ExerciseViewHolder(@NonNull View itemView, ExerciseListAdapter exerciseListAdapter) {
            super(itemView);
            this.textViewItem = itemView.findViewById(R.id.exerciseItem);
            this.exerciseListAdapter = exerciseListAdapter;
            this.favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
    //favoritos
    public interface OnFavoriteClickListener {
        void onFavoriteClick(int position);

    }

    private OnFavoriteClickListener favoriteClickListener;

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        favoriteClickListener = listener;
    }

    private void agregarEjercicioAFavoritos(String title) {
        favoriteExerciseTitles.add(title);

    }

    private void quitarEjercicioDeFavoritos(String title) {
        favoriteExerciseTitles.remove(title);
    }
}
