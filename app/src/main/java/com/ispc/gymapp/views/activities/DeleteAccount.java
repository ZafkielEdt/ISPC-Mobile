package com.ispc.gymapp.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.model.User;

public class DeleteAccount extends AppCompatActivity {

    private User user;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getUser() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            DocumentReference usernameRef = db.collection("users").document(firebaseUser.getUid());
            usernameRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {

                    user = documentSnapshot.toObject(User.class);
                }
            });

        }
    }

    public void deleteAccount(View view) {
        user = new User();
        getUser();
        db.collection("users").whereEqualTo("mail", user.getMail())
                .get().addOnCompleteListener(task -> {
                    // Get document id
                    String documentId = "";

                    for (DocumentSnapshot documentSnapshot : task.getResult()){
                        documentId = documentSnapshot.getId();
                        // Delete
                        db.collection("users").document(documentId)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("DELETE COMPLETE", "DELETE SUCCESSFULLY");
                                        //Intent intent = new Intent(this, LoginActivity.class);
                                        //startActivity(intent);
                                    }
                                });
                    }
                });
    }

    public void returnToProfile(View view) {
        this.onBackPressed();
    }
}