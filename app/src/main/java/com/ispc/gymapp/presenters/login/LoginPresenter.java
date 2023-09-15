package com.ispc.gymapp.presenters.login;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.views.activities.MainActivity;

import java.util.concurrent.Executor;

public class LoginPresenter {


    private final String TAG = "LoginPresenter";
    private Context ctx;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;



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
                            Intent intent = new Intent(ctx, MainActivity.class);
                            ctx.startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ctx, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void signOut(){
        mAuth.signOut();
    }


}
