package com.ispc.gymapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.views.fragments.MiPerfilFragment;

import java.util.List;

public class DietExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    public FirebaseFirestore db ;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_exercise);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);
        setupNavigation(bottomNavigationView);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        CardView cardViewBreakfast = findViewById(R.id.cardViewBreakfast);
        CardView cardViewExercise = findViewById(R.id.cardView);
        CardView cardViewLunch = findViewById(R.id.cardViewLunch);
        CardView cardViewDinner = findViewById(R.id.cardViewDinner);
        for (CardView card : List.of(cardViewLunch, cardViewExercise, cardViewBreakfast, cardViewDinner)) {
            card.setOnClickListener(this);
        }

    }


    private void setupNavigation(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setSelectedItemId(R.id.title_activity_exercise);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.title_activity_exercise) {
                    return true;
                }

                if (id == R.id.home) {
                    startActivitySafely(MainActivity.class);
                    return true;
                }

                if (id == R.id.shopItem) {
                    startActivitySafely(Ecommerce.class);
                    return true;
                }

                if (id == R.id.accountItem) {
                    startActivitySafely(MiPerfilFragment.class);
                    return true;
                }

                return false;

            }
            private void startActivitySafely(Class<?> cls) {
                if (!cls.isInstance(this)) {
                    startActivity(new Intent(getApplicationContext(), cls));
                    overridePendingTransition(0, 0);
                }
            }


        });
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.cardView){
            Intent intent = new Intent(DietExerciseActivity.this, ExerciseList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        if (view.getId() == R.id.cardViewBreakfast) {
            // Iniciar el fragmento de Desayuno con el tipo de comida como argumento
            Intent intent = new Intent(this, MealActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("mealType", "breakfast");
            startActivity(intent);
        } else if (view.getId() == R.id.cardViewLunch) {
            // Iniciar el fragmento de Almuerzo con el tipo de comida como argumento
            Intent intent = new Intent(this, MealActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("mealType", "lunch");
            startActivity(intent);
        } else if (view.getId() == R.id.cardViewDinner) {
            // Iniciar el fragmento de Cena con el tipo de comida como argumento
            Intent intent = new Intent(this, MealActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("mealType", "dinner");
            startActivity(intent);
        }


        }
    }


