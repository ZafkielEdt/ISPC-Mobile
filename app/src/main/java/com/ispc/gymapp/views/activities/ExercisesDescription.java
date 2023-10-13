package com.ispc.gymapp.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.model.Routine;

import java.util.ArrayList;
import java.util.Objects;

public class ExercisesDescription extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Exercise> exercises;

    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_description);
        Intent intent = getIntent();
        String title = intent.getStringExtra(ExerciseList.EXTRA_EXERCISE_TYPE);
        exercises = new ArrayList<>();
        getExercise(title);
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
        if(title.contains("Abdominales")) {
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

        db = FirebaseFirestore.getInstance();
        // Document Reference
        DocumentReference newRoutine = db.collection("routines").document();
        // New Routine
        Routine routine = new Routine(newRoutine.getId(), title, level, "", muscleGroup, exercise);
        // Create routine
        create(routine.getTitle(), newRoutine, routine);
        // Intent
        Intent intent = new Intent(this, RoutineActivity.class);
        // Go to Routine Activity
        startActivity(intent);
    }

    public void returnToExercises(View view) {
        this.onBackPressed();
    }

    private void create(String title, DocumentReference newRoutine, Routine routine) {
        CollectionReference routinesReference = db.collection("routines");
        Query query = routinesReference.whereEqualTo("title",title);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()) {
                    newRoutine.set(routine);
                }
            }
        });
    }
}