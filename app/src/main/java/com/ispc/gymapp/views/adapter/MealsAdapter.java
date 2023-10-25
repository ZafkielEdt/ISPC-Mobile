package com.ispc.gymapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Meal;

import java.util.List;

public class MealsAdapter extends FirestoreRecyclerAdapter<Meal, MealsAdapter.MealViewHolder> {

    private Context context;
    public MealsAdapter(Context context,@NonNull FirestoreRecyclerOptions<Meal> options) {
        super(options);
        this.context = context;
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
        Glide.with(context)
                .load(model.getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.almuerzo))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);
    }



    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView descriptionTextView;
        public ImageView image;

        public MealViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            image = itemView.findViewById(R.id.mealImageView);


        }
    }

}