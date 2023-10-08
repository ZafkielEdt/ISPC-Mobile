package com.ispc.gymapp.views.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.helper.ViewModelFactory;
import com.ispc.gymapp.views.viewmodel.MealsViewModel;

public class MealsFragment extends Fragment {

    private MealsViewModel mViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Context context;

    public MealsFragment() {
    }

    public MealsFragment(Context context, FirebaseAuth mAuth, FirebaseFirestore db) {
        this.context = context;
        this.mAuth = mAuth;
        this.db = db;
    }


    public static MealsFragment newInstance() {
        return new MealsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_meals, container, false);
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(context, mAuth, db))
                .get(MealsViewModel.class);

        return rootView;
    }



}