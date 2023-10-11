package com.ispc.gymapp.views.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;

public class FireStoreRoutineAdapter extends FirestoreRecyclerAdapter<Routine, FireStoreRoutineAdapter.RoutineViewHolder> {


    public FireStoreRoutineAdapter(@NonNull FirestoreRecyclerOptions<Routine> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FireStoreRoutineAdapter.RoutineViewHolder holder, int position, @NonNull Routine model) {
        String title = model.getTitle();
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

    @NonNull
    @Override
    public FireStoreRoutineAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_item, parent, false);
        return new RoutineViewHolder(view);
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routineItem;
        ImageButton deleteBtn;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            routineItem = itemView.findViewById(R.id.routineItem);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String title = (String) view.getContentDescription();
            // Get id and delete
            db.collection("routines")
                    .whereEqualTo("title", title)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                delete(document.getId());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    });
        }

        private void delete(String id) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("routines").document(id)
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d("TAG", "DocumentSnapshot successfully deleted!"))
                    .addOnFailureListener(e -> Log.w("TAG", "Error deleting document", e));
        }
    }
}
