package com.ispc.gymapp.views.activities;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Role;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.presenters.register.RegisterPresenter;
import com.ispc.gymapp.views.adapter.OnboardingStateAdapter;

import java.time.LocalDate;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterPresenter registerPresenter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private OnboardingStateAdapter onboardingStateAdapter;
    private EditText registerName;
    private EditText registerWeight;
    private EditText registerGoal;
    private int currentFragmentIndex = -1;
    ViewPager2 viewPager;

    private EditText registerMail;
    private EditText registerPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnNex = findViewById(R.id.btnNext);
//        Button btnPrev = findViewById(R.id.btnPrev);
        onboardingStateAdapter = new OnboardingStateAdapter(this.getSupportFragmentManager(),getLifecycle());
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(onboardingStateAdapter);
        mAuth = FirebaseAuth.getInstance();
        btnNex.setOnClickListener(this);
//        btnPrev.setOnClickListener(this);
//        registerName = findViewById(R.id.registerName);
//        registerWeight = findViewById(R.id.registerWeight);
//        registerGoal = findViewById(R.id.registerGoal);
        registerMail = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerPresenter = new RegisterPresenter(this,db,mAuth);

    }

    @Override
    public void onClick(View view) {

//        if(view.getId() == R.id.btnRegister){
//            String mail = registerMail.getText().toString().trim();
//            String password = registerPassword.getText().toString().trim();
////            String name = registerName.getText().toString().trim();
////            Double weight = Double.parseDouble(registerWeight.getText().toString().trim());
////            Double goal = Double.parseDouble(registerGoal.getText().toString().trim());
////            Integer height = 166;
//            Role role = new Role("USER");
//
//            User user = new User(mail,password,role);
//            registerPresenter.registerUser(user);
//            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//        }

        if(view.getId() == R.id.btnNext){

            if (currentFragmentIndex < onboardingStateAdapter.getItemCount() - 1) {
                System.out.println(currentFragmentIndex);
                currentFragmentIndex++;
                viewPager.setCurrentItem(currentFragmentIndex);
            }

        }
        if(view.getId()==R.id.btnPrev){
            System.out.println(currentFragmentIndex);
            if(currentFragmentIndex<onboardingStateAdapter.getItemCount() && currentFragmentIndex!=1){
                currentFragmentIndex--;
                viewPager.setCurrentItem(currentFragmentIndex);
            }

        }

    }
}