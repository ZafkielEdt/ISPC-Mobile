package com.ispc.gymapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.helper.ViewModelFactory;
import com.ispc.gymapp.model.Meal;
import com.ispc.gymapp.views.activities.DietExerciseActivity;
import com.ispc.gymapp.views.activities.MealActivity;
import com.ispc.gymapp.views.adapter.MealsAdapter;
import com.ispc.gymapp.views.viewmodel.MealsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealsFragment extends Fragment {

    private MealsViewModel mViewModel;
    private String mealType;
    private RecyclerView recyclerView;
    private MealsAdapter adapter;
    private FloatingActionButton floatingActionButton;

    public MealsFragment() {
    }


    public static MealsFragment newInstance(String mealType) {
        MealsFragment fragment = new MealsFragment();
        Bundle args = new Bundle();
        args.putString("mealType", mealType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mealType = getArguments().getString("mealType");
        }
        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);
        FirebaseAuth mAuth = ((MealActivity) requireActivity()).mAuth;
        FirebaseFirestore db = ((MealActivity) requireActivity()).db;
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getActivity(), mAuth, db))
                .get(MealsViewModel.class);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        adapter = new MealsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        if (mealType != null) {
            LiveData<List<Meal>> filteredMealsLiveData = mViewModel.getMeals();
            filteredMealsLiveData.observe(getViewLifecycleOwner(), meals -> {
                System.out.println(meals);
                // Actualiza el adaptador del RecyclerView con las comidas filtradas
                adapter.updateData(meals);
            });
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        floatingActionButton = rootView.findViewById(R.id.btnBack);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        return rootView;
    }




}