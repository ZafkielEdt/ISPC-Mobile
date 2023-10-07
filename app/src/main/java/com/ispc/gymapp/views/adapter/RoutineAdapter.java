package com.ispc.gymapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    Context context;

    private final ArrayList<Routine> routines;

    public RoutineAdapter(Context context, ArrayList<Routine> routines) {
        this.context = context;
        this.routines = routines;
    }

    @NonNull
    @Override
    public RoutineAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem = LayoutInflater.from(context).inflate(R.layout.routine_item, parent, false);
        return new RoutineViewHolder(viewItem, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.RoutineViewHolder holder, int position) {
        Routine routine = routines.get(position);
        String title = routine.getTitle();
        holder.routineItem.setText(title);

        if (title.contains("Abdominales")) {
            holder.routineItem.setBackgroundResource(R.drawable.abs);
        } else if (title.contains("Pecho")) {
            holder.routineItem.setBackgroundResource(R.drawable.chest);
        } else if (title.contains("Brazo")) {
            holder.routineItem.setBackgroundResource(R.drawable.arm);
        } else if (title.contains("Pierna")) {
            holder.routineItem.setBackgroundResource(R.drawable.leg);
        } else if (title.contains("Espalda")){
            holder.routineItem.setBackgroundResource(R.drawable.back);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder {

        public final TextView routineItem;

        public final ImageButton deleteBtn;

        final RoutineAdapter routineAdapter;

        public RoutineViewHolder(@NonNull View itemView, RoutineAdapter routineAdapter) {
            super(itemView);
            this.routineItem = itemView.findViewById(R.id.routineItem);
            this.deleteBtn = itemView.findViewById(R.id.deleteBtn);
            this.routineAdapter = routineAdapter;
        }
    }
}
