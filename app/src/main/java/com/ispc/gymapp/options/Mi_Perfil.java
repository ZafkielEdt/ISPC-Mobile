package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ispc.gymapp.R;

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
}