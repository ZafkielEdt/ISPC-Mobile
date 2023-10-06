package com.ispc.gymapp.views.fragments;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.views.adapter.ExerciseListAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesBeginnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisesBeginnerFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<Exercise> exercises;

    ExerciseListAdapter exerciseListAdapter;

    FirebaseFirestore db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExercisesBeginnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExercisesBeginner.
     */
    // TODO: Rename and change types and number of parameters
    public static ExercisesBeginnerFragment newInstance(String param1, String param2) {
        ExercisesBeginnerFragment fragment = new ExercisesBeginnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises_beginner, container, false);


        recyclerView = view.findViewById(R.id.recyclerExercises);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        db = FirebaseFirestore.getInstance();
        exercises = new ArrayList<>();
        String exerciseType = "beginner";
        exerciseListAdapter = new ExerciseListAdapter(view.getContext(), exercises);

        recyclerView.setAdapter(exerciseListAdapter);

        getBeginnerExercises();


        //GetExercises();

        return view;
    }

    private void getBeginnerExercises() {
        db.collection("exercises").orderBy("title").whereEqualTo("type", "beginner")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore error", Objects.requireNonNull(error.getMessage()));
                        return;
                    }

                    for (DocumentChange documentChange : value.getDocumentChanges()) {

                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            exercises.add(documentChange.getDocument().toObject(Exercise.class));
                        }

                        exerciseListAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void GetExercises() {

        db.collection("exercises").orderBy("title").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.e("Firestore error", Objects.requireNonNull(error.getMessage()));
                    return;
                }

                for (DocumentChange documentChange : value.getDocumentChanges()) {

                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        exercises.add(documentChange.getDocument().toObject(Exercise.class));
                    }

                    exerciseListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}