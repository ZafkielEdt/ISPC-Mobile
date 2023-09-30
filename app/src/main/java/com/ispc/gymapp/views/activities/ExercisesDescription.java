package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ispc.gymapp.R;

public class ExercisesDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_description);
        Intent intent = getIntent();
        String title = intent.getStringExtra(ExerciseList.EXTRA_EXERCISE_TYPE);
        assert title != null;
        setTitleType(title);
    }

    private void setTitleType(String type) {
        TextView titleDescription = findViewById(R.id.descriptionTypeTitle);
        switch (type) {
            case "abs" -> titleDescription.setText("Abdominales");
            case "chest" -> titleDescription.setText("Pecho");
            case "arm" -> titleDescription.setText("Brazos");
            case "leg" -> titleDescription.setText("Piernas");
            default -> titleDescription.setText("Espalda y hombros");
        }
    }

    public void returnToExercises(View view) {
        finish();
    }
}