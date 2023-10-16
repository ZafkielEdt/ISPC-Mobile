package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.views.adapter.RoutineAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class RoutineActivity extends AppCompatActivity {

    RoutineAdapter routineAdapter;

    ArrayList<Routine> routines;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        setUpRecyclerView();
    }

    private void getRoutines() {
        db.collection("routines").orderBy("title").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore error", Objects.requireNonNull(error.getMessage()));
                return;
            }

            for (DocumentChange documentChange : value.getDocumentChanges()) {

                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    routines.add(documentChange.getDocument().toObject(Routine.class));
                }

                routineAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpRecyclerView() {
        routines = new ArrayList<>();
        // Adapter
        routineAdapter = new RoutineAdapter(this, routines);
        getRoutines();
        // Recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerRoutine);
        recyclerView.setAdapter(routineAdapter);
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