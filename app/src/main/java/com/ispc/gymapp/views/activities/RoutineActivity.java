package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.views.adapter.RoutineAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class RoutineActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<Routine> routines;

    RoutineAdapter routineAdapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        recyclerView = findViewById(R.id.recyclerRoutine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        routines = new ArrayList<>();
        routineAdapter = new RoutineAdapter(this, routines);

        recyclerView.setAdapter(routineAdapter);

        getRoutines();

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
}