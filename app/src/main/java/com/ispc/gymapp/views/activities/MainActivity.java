package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.presenters.login.LoginPresenter;
import com.ispc.gymapp.views.activities.LoginActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textView;
    private LoginPresenter loginPresenter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLogout = findViewById(R.id.btnLogout);
        textView = findViewById(R.id.mainTextTitle);
        btnLogout.setOnClickListener(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(this, mAuth,db);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
        System.out.println(firebaseUser.getUid());
            // Usar el UID del usuario actual para buscar el documento en Firestore
            DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());

            usernameRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        System.out.println(user.toString());
                        String name = user.getName();
                        String message = getString(R.string.saludo, name);
                        textView.setText(message);
                    } else {
                        // El documento no existe para este usuario
                        textView.setText(getString(R.string.saludo, "Anonimo"));
                    }
                }
            });

        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLogout){
            loginPresenter.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}