package com.ispc.gymapp.views.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.ispc.gymapp.R;
import com.ispc.gymapp.views.activities.MainActivity;

import android.util.Log;

import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MiPerfilFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String nombre;
        String email;

        if (firebaseUser != null) {
            nombre = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail(); // Obtener el correo electrónico del usuario
        } else {
            nombre = "Mi perfil";
            email = "Correo no disponible";
            Log.d("MiPerfilFragment", "El usuario actual es nulo");
            // Aquí puedes manejar la situación de un usuario nulo, si es necesario.
        }

        // Setea el texto del TextView "username" con el nombre del usuario
        TextView username = findViewById(R.id.username);
        username.setText(nombre);

        // Setea el texto del TextView "email" con el correo electrónico del usuario
        TextView userEmail = findViewById(R.id.email);
        userEmail.setText( email);
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
