package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ispc.gymapp.R;

public class RoutineDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);
    }

    public void returnToRoutine(View view) {
        this.onBackPressed();
    }
}