package com.ispc.gymapp.views.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.views.activities.RoutineDescription;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    Context context;

    private final ArrayList<Routine> routines;

    public final static String ROUTINE_DESCRIPTION_EXTRA = "com.ispc.gymapp.extra.Routine_Type";

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
        holder.deleteBtn.setContentDescription(title);

        if (title.contains("Abdominales")) {
            holder.routineItem.setBackgroundResource(R.drawable.abs);
        } else if (title.contains("Pecho")) {
            holder.routineItem.setBackgroundResource(R.drawable.chest);
        } else if (title.contains("Brazo")) {
            holder.routineItem.setBackgroundResource(R.drawable.arm);
        } else if (title.contains("Pierna")) {
            holder.routineItem.setBackgroundResource(R.drawable.leg);
        } else if (title.contains("Espalda")) {
            holder.routineItem.setBackgroundResource(R.drawable.back);
        }
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView routineItem;

        public final ImageButton deleteBtn;

        final RoutineAdapter routineAdapter;

        public RoutineViewHolder(@NonNull View itemView, RoutineAdapter routineAdapter) {
            super(itemView);
            this.routineItem = itemView.findViewById(R.id.routineItem);
            this.deleteBtn = itemView.findViewById(R.id.deleteBtn);
            this.routineAdapter = routineAdapter;
            deleteBtn.setOnClickListener(this);
            routineItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String title = (String) view.getContentDescription();

            if (view.getId() == R.id.deleteBtn) {
                // Get id and delete
                db.collection("routines")
                        .whereEqualTo("title", title)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    deleteFromDb(document.getId());
                                    routines.removeIf(routine -> routine.getTitle().equals(title));
                                    routineAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });
            } else if (view.getId() == R.id.routineItem) {
                goToDescription(view);
            }

        }

        private void deleteFromDb(String id) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("routines").document(id)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
        }

        // Go to description activity
        private void goToDescription(View view) {
            Intent intent = new Intent(view.getContext(), RoutineDescription.class);
            TextView textView = (TextView) view;
            String title = (String) textView.getText();
            intent.putExtra(ROUTINE_DESCRIPTION_EXTRA, title);
            view.getContext().startActivity(intent);
        }
    }
}