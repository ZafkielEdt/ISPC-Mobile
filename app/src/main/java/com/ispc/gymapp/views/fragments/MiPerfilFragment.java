package com.ispc.gymapp.views.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ispc.gymapp.R;
import com.ispc.gymapp.views.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiPerfilFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        // Initialize TextViews
        TextView usernameTextView = findViewById(R.id.name); // Actualiza la referencia al TextView del nombre
        TextView emailTextView = findViewById(R.id.email); // Inicializa emailTextView correctamente

        // Set default values

        String email = "Correo no disponible";

        // Check if the user is logged in
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String name = firebaseUser.getDisplayName(); // Obtiene el nombre desde Firebase
            if (name != null && !name.isEmpty()) {
                // Set the name to the TextView
                usernameTextView.setText(name);
            }
            email = firebaseUser.getEmail();
        }

        // Set text for the email TextView
        emailTextView.setText(email); // Establece el texto en emailTextView
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void returnToHome(View view) {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}
