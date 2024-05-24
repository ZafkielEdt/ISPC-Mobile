package com.ispc.gymapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.widget.ImageView;
import android.view.View.OnClickListener;



import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.presenters.login.LoginPresenter;
import com.ispc.gymapp.views.fragments.MealDirectAccessFragment;

public class MainActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView textView;
    private LoginPresenter loginPresenter;
    private User user;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());

            usernameRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {

                    user = documentSnapshot.toObject(User.class);
                    if (user != null) {

                    }
                } else {
                    // El documento no existe para este usuario
                    textView.setText(getString(R.string.saludo, "Anonimo"));
                }
            });

        }
    }

    public void textoClickeable(View view) {
        Intent intent = new Intent(this, Ecommerce.class); // Reemplaza "OtraActividad" por el nombre de la actividad a la que deseas redirigir.
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.mainTextTitle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);


//        ImageView profileImage = findViewById(R.id.profileImage);
//        profileImage.setOnClickListener(v -> {
//            Intent intent = new Intent(this, MiPerfilActivity.class);
//            startActivity(intent);
//        });



        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                return true;
            }

            if (id == R.id.title_activity_exercise) {
                startActivity(new Intent(getApplicationContext(), DietExerciseActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.shopItem) {
                startActivity(new Intent(getApplicationContext(), Ecommerce.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.accountItem) {
                startActivity(new Intent(getApplicationContext(), MiPerfilActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;

        });


    }

    public void openExerciseList(View view) {
        Intent intent = new Intent(this, ExerciseList.class);
        startActivity(intent);
    }

    public void openDiet(View view) {
        Intent intent = new Intent(this, DietExerciseActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view) {
        startActivity(new Intent(getApplicationContext(), MiPerfilActivity.class));
    }
}
