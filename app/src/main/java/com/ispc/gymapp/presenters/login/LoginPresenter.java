package com.ispc.gymapp.presenters.login;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.helper.Callback;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.views.activities.MainActivity;
import com.ispc.gymapp.views.activities.OnboardingActivityView;

import java.util.Optional;
import java.util.concurrent.Executor;

public class LoginPresenter {


    private final String TAG = "LoginPresenter";
    private Context ctx;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private User currentUser;
    private String userId;


    public LoginPresenter(Context ctx,FirebaseAuth mAuth,FirebaseFirestore db) {
        this.ctx = ctx;
        this.mAuth = mAuth;
        this.db = db;
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Log.d(TAG,task.getResult().getUser().getUid());
                            FirebaseUser firebaseUser = task.getResult().getUser();
//                            if (firebaseUser != null){
//                                DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());
//
//                                usernameRef.get().addOnSuccessListener(documentSnapshot -> {
//                                    if (documentSnapshot.exists()) {
//
//                                        currentUser = documentSnapshot.toObject(User.class);
//                                        userId = firebaseUser.getUid();
//                                        if(currentUser.getIMC()==null){
//                                            Intent intent = new Intent(ctx, OnboardingActivityView.class);
//                                            ctx.startActivity(intent);
//                                        }else{
//                                            Intent intent = new Intent(ctx, MainActivity.class);
//                                            ctx.startActivity(intent);
//                                        }
//                                    } else {
//                                        signOut();
//                                    }
//                                });
//
//                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ctx, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void getCurrentUser(Callback<User> callback) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());
            usernameRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        User loggedUser = documentSnapshot.toObject(User.class);
                        if (loggedUser != null) {
                            callback.onSuccess(loggedUser);
                        }
                    } else {
                        callback.onFailure(); // El usuario no existe en Firestore
                    }
                }
            });
        } else {
            callback.onFailure(); // No hay usuario autenticado
        }
    }



    public void signOut(){
        mAuth.signOut();
    }


}
