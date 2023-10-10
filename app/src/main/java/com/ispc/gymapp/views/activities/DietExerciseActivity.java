package com.ispc.gymapp.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ispc.gymapp.R;
import com.ispc.gymapp.views.fragments.MiPerfilFragment;

import java.util.List;

public class DietExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_exercise);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);
        setupNavigation(bottomNavigationView);

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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                if (id == R.id.shopItem) {
                    startActivity(new Intent(getApplicationContext(), Ecommerce.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                if (id == R.id.accountItem) {
                    startActivity(new Intent(getApplicationContext(), MiPerfilFragment.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;

            }


        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cardView){
            Intent intent = new Intent(DietExerciseActivity.this, ExerciseList.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.cardViewBreakfast){

        }
        if(view.getId() == R.id.cardViewLunch){

        }
        if(view.getId() == R.id.cardViewDinner){

        }
    }


}