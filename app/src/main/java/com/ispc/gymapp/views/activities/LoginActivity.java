package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.presenters.login.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LoginPresenter loginPresenter;
    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView btnToRegister = findViewById(R.id.btnToRegister);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        btnLogin.setOnClickListener(this);
        btnToRegister.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this,mAuth,db);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (isValid(username, password)) {
            loginPresenter.signIn(username,password);
            }
        }
        if(view.getId() == R.id.btnToRegister){
            finish();
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    }

    private boolean isValid(String username, String password){

        if (username.isEmpty()) {
            usernameEditText.setError("El nombre de usuario es requerido.");
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("La contraseña es requerida.");
            return false;
        }


        return true;

    }
}