package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.model.User;


import java.util.ArrayList;
import java.util.Objects;

public class ExercisesDescription extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Exercise> exercises;

    Exercise exercise;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_description);
        Intent intent = getIntent();
        String title = intent.getStringExtra(ExerciseList.EXTRA_EXERCISE_TYPE);
        exercises = new ArrayList<>();
        getExercise(title);
        // Get current user
        getUser();
    }

    private void getExercise(String title) {
        db.collection("exercises").orderBy("title").whereEqualTo("title", title)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.e("Firestore error", Objects.requireNonNull(error.getMessage()));
                        return;
                    }

                    for (DocumentChange documentChange : value.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            exercises.add(documentChange.getDocument().toObject(Exercise.class));
                        }
                        exercise = exercises.get(0);
                        setData(exercise);
                    }

                });
    }

    private void setData(Exercise exercise) {
        // Set main title
        TextView mainTitle = findViewById(R.id.mainTitle);
        mainTitle.setText(exercise.getTitle());
        // Set duration
        Button duration = findViewById(R.id.durationBtn);
        duration.setText(getString(R.string.default_time_text, exercise.getDuration()));
        // Set kal
        Button cal = findViewById(R.id.calButton);
        cal.setText(getString(R.string.default_cal, exercise.getCaloriesBurned()));
        // Set subtitle
        TextView secondSubtitle = findViewById(R.id.secondSubtitle);
        if (exercise.getTitle().contains("Principiante")) {
            secondSubtitle.setText("Ejercicios Principiante");
        } else if (exercise.getTitle().contains("Intermedio")) {
            secondSubtitle.setText("Ejercicios Intermedio");
        } else {
            secondSubtitle.setText("Ejercicios Avanzado");
        }
        // Set description
        TextView textView = findViewById(R.id.descriptionText);
        textView.setText(exercise.getDescription());

    }

    public void createRoutine(View view) {
        // Get title
        String title = exercise.getTitle();
        // Get level
        String level = "";
        if (title.contains("Principiante")) {
            level = "Principiante";
        } else if (title.contains("Intermedio")) {
            level = "Intermedio";
        } else {
            level = "Avanzado";
        }
        // Get muscleGroup
        String muscleGroup = "";
        if (title.contains("Abdominales")) {
            muscleGroup = "Abdominales";
        } else if (title.contains("Pecho")) {
            muscleGroup = "Pecho";
        } else if (title.contains("Brazo")) {
            muscleGroup = "Brazo";
        } else if (title.contains("Pierna")) {
            muscleGroup = "Pierna";
        } else if (title.contains("Espalda")) {
            muscleGroup = "Espalda y hombro";
        }

        Routine routine = new Routine();
        routine.setTitle(title);
        routine.setLevel(title);
        routine.setMuscleGroup(muscleGroup);

        db = FirebaseFirestore.getInstance();

        // Check if user have a routine
        db.collection("routines").whereEqualTo("user", user.getMail())
                .get().addOnCompleteListener(task -> {
                    if (task.getResult().isEmpty()) {
                        // Create new routine attached to current user
                        // New Set
                        ArrayList<Exercise> exerciseList = new ArrayList<>();
                        exerciseList.add(exercise);
                        // Document Reference
                        DocumentReference newRoutine = db.collection("routines").document();
                        routine.setId(newRoutine.getId());
                        routine.setExercises(exerciseList);
                        routine.setUser(user.getMail());
                        // Create
                        newRoutine.set(routine);
                    } else {
                        // Update routine if user already have one
                        Routine currentRoutine = new Routine();
                        String id = "";
                        for (DocumentChange documentChange : task.getResult().getDocumentChanges()) {
                            if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                currentRoutine = documentChange.getDocument().toObject(Routine.class);
                                id = documentChange.getDocument().getId();
                            }
                        }
                        // Add item if not exist
                        boolean itemExist = false;
                        if (currentRoutine.getExercises().contains(exercise)) {
                            Log.i("TAG", "Item already exist");
                            itemExist = true;
                            Toast.makeText(this, "Ejercicio ya agregado",Toast.LENGTH_LONG).show();
                        } else {
                            itemExist = false;
                            currentRoutine.getExercises().add(exercise);
                        }
                        db.collection("routines").document(id).set(currentRoutine);
                        if (!itemExist) {
                            // Intent
                            Intent intent = new Intent(this, RoutineActivity.class);
                            // Go to Routine Activity
                            startActivity(intent);
                        }
                    }
                });
    }

    public void returnToExercises(View view) {
        this.onBackPressed();
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