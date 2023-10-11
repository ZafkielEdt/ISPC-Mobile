package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.views.adapter.ExercisesAdapter;
import com.ispc.gymapp.views.adapter.MealsAdapter;
import com.ispc.gymapp.views.fragments.MealsFragment;

public class MealActivity extends AppCompatActivity {

    private MealsAdapter mealsAdapter;
    public FirebaseFirestore db ;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Recuperar datos pasados desde DietExerciseActivity
        Intent intent = getIntent();
        if (intent != null) {
            String mealType = intent.getStringExtra("mealType");

            // Cargar el fragmento MealsFragment con los datos relevantes (mealType)
            MealsFragment mealsFragment = MealsFragment.newInstance(mealType);

            // Iniciar la transacción para agregar el fragmento
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, mealsFragment) // R.id.fragmentContainer es el contenedor del fragmento en activity_meals.xml
                    .commit();
        }


    }
}