package com.ispc.gymapp.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;



public class ContactActivity extends Activity  {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
    }
    public void returnToHome(View view) {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

}