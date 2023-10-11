package com.ispc.gymapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Meal;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
    private List<Meal> meals;

    public MealsAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        System.out.println(meal);
        holder.nameTextView.setText(meal.getName());
        holder.descriptionTextView.setText(meal.getDescription());


    }

    @Override
    public int getItemCount() {
        return meals.size();
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