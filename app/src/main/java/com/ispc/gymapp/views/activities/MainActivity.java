package com.ispc.gymapp.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.presenters.login.LoginPresenter;
import com.ispc.gymapp.views.fragments.MiPerfilFragment;

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

            usernameRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        user = documentSnapshot.toObject(User.class);
                        if (user != null) {

                            System.out.println(user.toString());
                            String name = user.getName();
                            String message = getString(R.string.saludo, name);
                            textView.setText(message);
                        }
                    } else {
                        // El documento no existe para este usuario
                        textView.setText(getString(R.string.saludo, "Anonimo"));
                    }
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.mainTextTitle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    return true;
                }

                if (id == R.id.title_activity_exercise) {
                    startActivity(new Intent(getApplicationContext(), ExerciseList.class));
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
}
