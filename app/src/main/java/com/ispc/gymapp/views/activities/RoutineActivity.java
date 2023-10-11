package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.views.adapter.FireStoreRoutineAdapter;

import java.util.ArrayList;

public class RoutineActivity extends AppCompatActivity {

    FireStoreRoutineAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        // Query
        Query query = db.collection("routines");
        // Options
        FirestoreRecyclerOptions<Routine> options = new FirestoreRecyclerOptions.Builder<Routine>()
                .setQuery(query, Routine.class)
                .setLifecycleOwner(this)
                .build();

        // Adapter
        adapter = new FireStoreRoutineAdapter(options);
        // Recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerRoutine);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void returnToDescription(View view) {
        this.finish();
    }

    public void returnToExerciseList(View view) {
        Intent intent = new Intent(this, ExerciseList.class);
        startActivity(intent);
    }
}