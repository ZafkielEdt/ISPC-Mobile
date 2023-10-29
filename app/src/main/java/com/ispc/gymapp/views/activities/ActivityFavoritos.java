package com.ispc.gymapp.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.views.adapter.ExerciseListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ActivityFavoritos extends AppCompatActivity implements ExerciseListAdapter.OnFavoriteClickListener{
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ExerciseListAdapter exerciseListAdapter;
    private ArrayList<String> favoriteExerciseTitles = new ArrayList<>();
    private FirebaseFirestore db; // Asegúrate de inicializar db
    private static final String TAG = "ActivityFavoritos";
    private DocumentReference userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();


        // Chequea si el usuario ya está autenticado
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // Si el usuario no está autenticado, redirige a la pantalla de inicio de sesión
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        String userId = currentUser.getUid();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        userRef = FirebaseFirestore.getInstance().collection("users").document("userId");

        db = FirebaseFirestore.getInstance(); // Inicializa db
        RecyclerView recyclerView = findViewById(R.id.recyclerview); // Reemplaza con el ID correcto
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Configura el LayoutManager
        exerciseListAdapter = new ExerciseListAdapter(this, exercises);
        exerciseListAdapter.setOnFavoriteClickListener(this);
        recyclerView.setAdapter(exerciseListAdapter);

        getAllExercises(); // Obtener ejercicios desde Firestore

        // Inicializar la lista de ejercicios favoritos desde las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        favoriteExerciseTitles = new ArrayList<>(sharedPreferences.getStringSet("favoriteExerciseTitles", new HashSet<>()));
        exerciseListAdapter.notifyDataSetChanged();


        // Obtener el documento del usuario y sus favoritos
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Obtiene la lista de favoritos del usuario
                        ArrayList<String> favoriteExerciseTitles = (ArrayList<String>) document.get("favoriteExerciseTitles");

                        for (String exerciseId : favoriteExerciseTitles) {
                            getAllExercises(); //
                        }
                    }
                } else {
                    Log.d(TAG, "Error obteniendo el documento del usuario", task.getException());
                }
            }
        });
    }

    // Método para agregar un ejercicio a favoritos
    private void agregarEjercicioAFavoritos(String title) {
        favoriteExerciseTitles.add(title);
        exerciseListAdapter.notifyDataSetChanged();

        // Agregar un favorito al usuario
        userRef.update("favoriteExerciseTitles", FieldValue.arrayUnion(title));

        // Obtener el usuario actualmente autenticado
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Referencia a la colección de usuarios en Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("users").document(userId);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            favoriteExerciseTitles = new ArrayList<>(sharedPreferences.getStringSet("favoriteExerciseTitles", new HashSet<>()));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("favoriteExerciseTitles", new HashSet<>(favoriteExerciseTitles));
            editor.apply();


        }


    }

    private void getAllExercises() {
        db.collection("exercises").orderBy("title")
                .addSnapshotListener((value, error) ->{
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                        return;
                    }

                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            Exercise exercise = documentChange.getDocument().toObject(Exercise.class);

                            if (favoriteExerciseTitles.contains(exercise.getTitle())) {
                                exercises.add(exercise);
                                Log.d("Exercise Added", exercise.getTitle());
                            }
                        }
                    }

                    exerciseListAdapter.notifyDataSetChanged();
                    Log.d("MiApp", "getAllExercises called");

                });
    }


    @Override
    public void onFavoriteClick(int position) {

        Exercise exercise = exercises.get(position);
        exercise.setFavorite(!exercise.isFavorite());
        System.out.println(exercise);

        /*favoriteExerciseTitles.clear();*/
        if (exercise.isFavorite()) {
            favoriteExerciseTitles.add(exercise.getTitle());
        } else {
            favoriteExerciseTitles.remove(exercise.getTitle());
        }
        // Guardar la lista actualizada en las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("favoriteExerciseTitles", new HashSet<>(favoriteExerciseTitles));
        editor.apply();


        exerciseListAdapter.notifyDataSetChanged();
    }

    private void obtenerFavoritosDeFirestore() {
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    ArrayList<String> favoritosEnFirestore = (ArrayList<String>) documentSnapshot.get("favoriteExerciseTitles");
                    if (favoritosEnFirestore != null) {

                        favoriteExerciseTitles.addAll(favoritosEnFirestore);


                        exerciseListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void returnToHome(View view) {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }


}