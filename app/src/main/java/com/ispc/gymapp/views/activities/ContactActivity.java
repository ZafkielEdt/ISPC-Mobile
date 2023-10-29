package com.ispc.gymapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ispc.gymapp.R;


public class ContactActivity extends Activity  {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contacto);
        }
        public void returnToHome(View view) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
        }

}