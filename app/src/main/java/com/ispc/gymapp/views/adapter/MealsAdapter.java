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
import com.ispc.gymapp.model.Meal;

import java.util.List;

public class MealsAdapter extends FirestoreRecyclerAdapter<Meal, MealsAdapter.MealViewHolder> {
    private List<Meal> meals;

    public MealsAdapter(@NonNull FirestoreRecyclerOptions<Meal> options) {
        super(options);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }
    @Override
    protected void onBindViewHolder(@NonNull MealViewHolder holder, int position, @NonNull Meal model) {

        holder.nameTextView.setText(model.getName());
        holder.descriptionTextView.setText(model.getDescription());
    }



    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;

        public MealViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);


        }
    }
    public void updateData(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }
}