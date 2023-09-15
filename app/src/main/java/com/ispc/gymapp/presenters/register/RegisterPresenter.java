package com.ispc.gymapp.presenters.register;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.views.activities.LoginActivity;
import com.ispc.gymapp.views.activities.MainActivity;
import com.ispc.gymapp.views.activities.RegisterActivity;

public class RegisterPresenter {
    private final String TAG = "RegisterPresenter";
    private final Context ctx;
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;

    private DocumentReference documentReference;

    public RegisterPresenter(Context ctx, FirebaseFirestore db, FirebaseAuth mAuth) {
        this.ctx = ctx;
        this.db = db;
        this.mAuth = mAuth;

    }

    public void registerUser(User user) {


        mAuth.createUserWithEmailAndPassword(user.getMail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            db.collection("roles")
                                    .whereEqualTo("name", user.getRole().getName())
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        if (task1.isSuccessful() && !task1.getResult().isEmpty()) {
                                            String id = firebaseUser.getUid();

                                            // Create a Firestore document with the UID as the document ID
                                            DocumentReference newUserRef = db.collection("users").document(id);

                                            // Set user data in the new document
                                            newUserRef
                                                    .set(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(ctx, "User added successfully.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Log.d(TAG, "Role not found with name: " + user.getRole().getName());
                                            Toast.makeText(ctx, "Role not found with name: " + user.getRole().getName(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ctx, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
