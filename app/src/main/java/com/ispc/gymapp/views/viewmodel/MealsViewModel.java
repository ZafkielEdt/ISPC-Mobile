package com.ispc.gymapp.views.viewmodel;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ispc.gymapp.helper.OnMealDataCallback;
import com.ispc.gymapp.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsViewModel extends ViewModel {
    private final Context ctx;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;
    private CollectionReference collectionReference;

    public MealsViewModel(Context ctx, FirebaseFirestore db, FirebaseAuth mAuth) {
        this.ctx = ctx;
        this.db = db;
        this.mAuth = mAuth;
    }




    public LiveData<List<Meal>> getMeals() {
        MutableLiveData<List<Meal>> mealsLiveData = new MutableLiveData<>();

        getMeals(new OnMealDataCallback() {
            @Override
            public void onMealsFetched(List<Meal> meals) {
                mealsLiveData.postValue(meals);
            }

            @Override
            public void onError(Exception e) {
            }
        });

        return mealsLiveData;
    }

    public List<Meal> getMeals(OnMealDataCallback onMealDataCallback){
        List<Meal> meals = new ArrayList<>();
        collectionReference = db.collection("meals");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Meal meal = documentSnapshot.toObject(Meal.class);
                    meals.add(meal);
                }


            }
        });
        return meals;
    }
}