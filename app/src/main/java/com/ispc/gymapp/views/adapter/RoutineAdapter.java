package com.ispc.gymapp.views.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.views.activities.RoutineDescription;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    Context context;

    private final ArrayList<Exercise> exercises;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private User user;

    public final static String ROUTINE_DESCRIPTION_EXTRA = "com.ispc.gymapp.extra.Routine_Type";

    public RoutineAdapter(Context context, ArrayList<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public RoutineAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem = LayoutInflater.from(context).inflate(R.layout.routine_item, parent, false);
        return new RoutineViewHolder(viewItem, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.RoutineViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        String title = exercise.getTitle();
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
        return exercises.size();
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
            getUser();
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.deleteBtn) {
                delete(view);
            } else if (view.getId() == R.id.routineItem) {
                goToDescription(view);
            }

        }

        private void delete(View view) {
            String title = (String) view.getContentDescription();
            // Get id and delete
            db.collection("routines").whereEqualTo("user", user.getMail())
                    .get().addOnCompleteListener(task -> {
                        // Get routine item
                        Routine currentRoutine = new Routine();
                        String id = "";
                        for (DocumentChange documentChange : task.getResult().getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                currentRoutine = documentChange.getDocument().toObject(Routine.class);
                                id = documentChange.getDocument().getId();
                            }
                            // Remove item from exercises list
                            currentRoutine.getExercises().removeIf(e -> e.getTitle().equals(title));
                            // Update content
                            exercises.clear();
                            exercises.addAll(currentRoutine.getExercises());
                            db.collection("routines").document(id).set(currentRoutine);
                            // Update adapter
                            routineAdapter.notifyDataSetChanged();
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

        private void getUser() {
            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            if (firebaseUser != null) {
                DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());
                usernameRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        user = documentSnapshot.toObject(User.class);
                    }
                });

            }
        }
    }
}