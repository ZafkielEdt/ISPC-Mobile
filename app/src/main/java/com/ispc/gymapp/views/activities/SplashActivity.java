package com.ispc.gymapp.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ispc.gymapp.R;

public class SplashActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            // Inicia el temporizador para mostrar el splash screen durante 3 segundos
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Llama al método onSplashScreenFinished()
                    onSplashScreenFinished();
                }
            }, 1500);
        }

        public void onSplashScreenFinished() {
                        // Inicia la actividad principal
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            // Cierra la actividad de splash screen
            finish();
        }
    }


