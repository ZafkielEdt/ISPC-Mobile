package com.ispc.gymapp.views.activities;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import com.ispc.gymapp.R;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class PassResetActivity extends AppCompatActivity {

    private EditText newPassword;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initializeUI();
        setupTextWatchers();
        setupButtonClick();
    }

    private void initializeUI() {
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
    }

    private void setupTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = editable.toString().trim();
                if (editable == newPassword.getEditableText()) {
                    if (!isValidPassword(password)) {
                        newPassword.setError("Invalid password");
                    } else {
                        newPassword.setError(null);
                    }
                } else if (editable == confirmPassword.getEditableText()) {
                    String password2 = confirmPassword.getText().toString().trim();
                    if (!password2.equals(password)) {
                        confirmPassword.setError("Passwords do not match");
                    } else {
                        confirmPassword.setError(null);
                    }
                }
            }
        };

        newPassword.addTextChangedListener(textWatcher);
        confirmPassword.addTextChangedListener(textWatcher);
    }

    private void setupButtonClick() {
        Button btnToResetPass = findViewById(R.id.btnToResetPass);
        btnToResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the previous activity
                finish(); // This will close the current activity and return to the previous one.
            }
        });
    }

    private boolean isValidPassword(String password) {
        // Define your own password validation logic here
        return password.length() >= 6; // For example, a valid password must be at least 6 characters long
    }
}