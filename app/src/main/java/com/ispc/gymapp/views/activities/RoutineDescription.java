package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.views.adapter.RoutineAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class RoutineDescription extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<Exercise> exercises;

    Exercise exercise;

    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);
        Intent intent = getIntent();
        String title = intent.getStringExtra(RoutineAdapter.ROUTINE_DESCRIPTION_EXTRA);
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
        TextView mainTitle = findViewById(R.id.mainTitleRoutine);
        mainTitle.setText(exercise.getTitle());
        // Set duration
        Button duration = findViewById(R.id.durationBtnRoutine);
        duration.setText(getString(R.string.default_time_text, exercise.getDuration()));
        // Set kal
        Button cal = findViewById(R.id.calButtonRoutine);
        cal.setText(getString(R.string.default_cal, exercise.getCaloriesBurned()));
        // Set subtitle
        TextView secondSubtitle = findViewById(R.id.secondSubtitleRoutine);
        if (exercise.getTitle().contains("Principiante")) {
            secondSubtitle.setText("Ejercicios Principiante");
        } else if (exercise.getTitle().contains("Intermedio")) {
            secondSubtitle.setText("Ejercicios Intermedio");
        } else {
            secondSubtitle.setText("Ejercicios Avanzado");
        }
        // Set url
        videoUrl = exercise.getVideoUrl();
        // Set description
        TextView textView = findViewById(R.id.descriptionTextRoutine);
        textView.setText(exercise.getDescription());

    }

    public void goToVideo(View view) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(webIntent);
    }

    public void returnToRoutine(View view) {
        this.onBackPressed();
    }
}