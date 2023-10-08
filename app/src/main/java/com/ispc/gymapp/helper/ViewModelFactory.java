package com.ispc.gymapp.helper;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.views.viewmodel.MealsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    public ViewModelFactory(Context context, FirebaseAuth mAuth, FirebaseFirestore db) {
        this.context = context;
        this.mAuth = mAuth;
        this.db = db;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MealsViewModel.class)) {
            // Crea una instancia de MealsViewModel y pasa las dependencias al constructor
            return (T) new MealsViewModel(context, db, mAuth);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}