package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.adapter.FireStoreRoutineAdapter;


public class RoutineDescription extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_description);
        Intent intent = getIntent();
        String title = intent.getStringExtra(FireStoreRoutineAdapter.ROUTINE_DESCRIPTION_EXTRA);
        TextView view = findViewById(R.id.mainTitleRoutine);
        view.setText(title);
    }

    public void returnToRoutine(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}