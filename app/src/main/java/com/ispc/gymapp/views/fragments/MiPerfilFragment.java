package com.ispc.gymapp.views.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ispc.gymapp.R;
import com.ispc.gymapp.views.activities.Ecommerce;
import com.ispc.gymapp.views.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.DecimalFormat;

public class MiPerfilFragment extends AppCompatActivity {

    private EditText pesoEditText;
    private EditText alturaEditText;
    private TextView imcTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        // Initialize TextViews
        TextView usernameTextView = findViewById(R.id.name); // Actualiza la referencia al TextView del nombre
        TextView emailTextView = findViewById(R.id.email); // Inicializa emailTextView correctamente

        // Set default values

        String email = "Correo no disponible";


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigator);
        bottomNavigationView.setSelectedItemId(R.id.title_activity_exercise);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

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

        // Inicializa los EditText y TextView para el cálculo del IMC
        pesoEditText = findViewById(R.id.peso);
        alturaEditText = findViewById(R.id.altura);
        imcTextView = findViewById(R.id.textView18);

        // Botón para calcular el IMC
        Button calcularIMCButton = findViewById(R.id.calcularIMC);
        calcularIMCButton.setOnClickListener(view -> calcularIMC());
    }

    @SuppressLint("SetTextI18n")
    private void calcularIMC() {
        String pesoStr = pesoEditText.getText().toString();
        String alturaStr = alturaEditText.getText().toString();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            // Convierte las entradas de texto a números
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            // Calcula el IMC
            double imc = peso / (altura * altura);

            // Formatea el resultado con dos decimales
            DecimalFormat df = new DecimalFormat("#.##");
            String resultadoIMC = df.format(imc);

            // Muestra el resultado en el TextView
            imcTextView.setText("IMC: " + resultadoIMC);
        } else {
            // Muestra un mensaje de error si falta peso o altura
            imcTextView.setText("Por favor, ingresa peso y altura.");
        }
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
