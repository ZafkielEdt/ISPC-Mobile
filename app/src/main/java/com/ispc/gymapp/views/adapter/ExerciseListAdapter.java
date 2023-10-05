package com.ispc.gymapp.views.adapter;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.activities.ExerciseList;

import java.util.LinkedList;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    private final LinkedList<String> exerciseList;

    private LayoutInflater mInflater;

    public ExerciseListAdapter(Context context, LinkedList<String> exerciseList) {
        mInflater = LayoutInflater.from(context);
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseListAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.exerciselist_item, parent, false);
        return new ExerciseViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListAdapter.ExerciseViewHolder holder, int position) {
        String mCurrent = exerciseList.get(position);
        holder.textViewItem.setText(mCurrent);

        switch (position) {
            case 1 -> {
                holder.textViewItem.setBackgroundResource(R.drawable.abs);
                holder.textViewItem.setId(position);
            }
            case 2 -> {
                holder.textViewItem.setBackgroundResource(R.drawable.chest);
                holder.textViewItem.setId(position);
            }
            case 3 -> {
                holder.textViewItem.setBackgroundResource(R.drawable.arm);
                holder.textViewItem.setId(position);
            }
            case 4 -> {
                holder.textViewItem.setBackgroundResource(R.drawable.leg);
                holder.textViewItem.setId(position);
            }
            case 5 -> {
                holder.textViewItem.setBackgroundResource(R.drawable.back);
                holder.textViewItem.setId(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        public final TextView textViewItem;
        final ExerciseListAdapter exerciseListAdapter;

        public final static String EXTRA_EXERCISE_TYPE = "com.ispc.gymapp.extra.Exercise_Type";

        private String exerciseType;


        public ExerciseViewHolder(@NonNull View itemView, ExerciseListAdapter exerciseListAdapter) {
            super(itemView);
            this.textViewItem = itemView.findViewById(R.id.exerciseItem);
            this.exerciseListAdapter = exerciseListAdapter;
        }
    }
}
