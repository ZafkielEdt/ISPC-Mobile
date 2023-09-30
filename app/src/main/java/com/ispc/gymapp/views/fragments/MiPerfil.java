package com.ispc.gymapp.views.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.activities.MainActivity;

public class MiPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);


    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void returnToHome(MenuItem item) {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}