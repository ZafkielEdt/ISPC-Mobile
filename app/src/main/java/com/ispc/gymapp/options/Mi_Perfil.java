package com.ispc.gymapp.options;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ispc.gymapp.R;

public class Mi_Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Mis Perfil");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}