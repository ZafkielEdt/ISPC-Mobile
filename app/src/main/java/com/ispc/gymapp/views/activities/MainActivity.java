package com.ispc.gymapp.views.activities;

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

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView textView;
    private LoginPresenter loginPresenter;
    private User user;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());

            usernameRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        user = documentSnapshot.toObject(User.class);
                        if(user!=null){

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

        setupNavegacion();
    }


    @Override
    public void onClick(View view) {


    }

    public void goToExercises(MenuItem menuItem) {
        Intent exercisesView = new Intent(this, ExerciseList.class);
        startActivity(exercisesView);
    }

    private void setupNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);

        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.getNavController()
        );
    }

}