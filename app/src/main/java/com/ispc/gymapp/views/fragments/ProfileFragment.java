package com.ispc.gymapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.views.activities.DietExerciseActivity;
import com.ispc.gymapp.views.activities.ExerciseList;
import com.ispc.gymapp.views.activities.RoutineActivity;

import android.content.Context;
import android.content.DialogInterface;
import androidx.fragment.app.DialogFragment; // Importa DialogFragment
import androidx.fragment.app.FragmentTransaction;
import java.text.DecimalFormat;
import com.ispc.gymapp.views.activities.ExerciseList;
import android.view.View;
import android.content.Intent;
import com.ispc.gymapp.model.Exercise;
import com.ispc.gymapp.model.Routine;
import com.ispc.gymapp.model.User;
import com.ispc.gymapp.views.adapter.RoutineAdapter;

import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    RoutineAdapter routineAdapter;

    ArrayList<Exercise> exercises;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText pesoEditText;
    private EditText alturaEditText;
    private TextView imcTextView;
    private User user;


    private TextView nameTextView;
    private TextView emailTextView;

    public ProfileFragment(User user) {
        this.user = user;
    }


    public static ProfileFragment newInstance(String param1, String param2,User user) {
        ProfileFragment fragment = new ProfileFragment(user);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        pesoEditText = view.findViewById(R.id.peso);
        alturaEditText = view.findViewById(R.id.altura);
        imcTextView = view.findViewById(R.id.textImc);
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);

        // Check if the user is logged in
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            String name = firebaseUser.getDisplayName();
            if (name != null && !name.isEmpty()) {
                nameTextView.setText(name);
            } else {
                // If the name is null or empty, set "Mi Perfil"
                nameTextView.setText("Mi Perfil");
            }
            String email = firebaseUser.getEmail();
            emailTextView.setText(email);
        }

        // Set hint messages for peso and altura fields
        pesoEditText.setHint("Click en editar para ingresar peso");
        alturaEditText.setHint("Click en editar para ingresar altura");

        // Calculate and set IMC (you can keep this part)
        String pesoStr = pesoEditText.getText().toString();
        String alturaStr = alturaEditText.getText().toString();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            double peso = Double.parseDouble(pesoStr);
            int altura = Integer.parseInt(alturaStr);

            double imc = peso / (altura * altura);

            DecimalFormat df = new DecimalFormat("#.##");
            String resultadoIMC = df.format(imc);

            imcTextView.setText("IMC: " + resultadoIMC);
        } else {
            imcTextView.setText("");
        }
        Button editProfileButton = view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el modal de edición
                openEditProfileDialog();
            }
        });

        return view;
    }
    private String imcCalculator(double weight, int height) {

        if(weight<=0 && height<=0) {
            return "";
        }
        double imcCalculator= weight / (height * height);
        if (imcCalculator<18.5) {
            return "BAJO PESO";
        } else if (imcCalculator>=18.5 && imcCalculator<=24.9) {
            return "NORMAL";
        } else if (imcCalculator>=25 && imcCalculator<=29.9) {
            return "SOBREPESO";
        } else {
            return "OBESIDAD";
        }
    }

    private void openEditProfileDialog() {
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = requireActivity().getSupportFragmentManager().findFragmentByTag("edit_profile_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Crea una instancia del fragmento de edición y muestra el diálogo
        DialogFragment editProfileDialog = EditProfileFragment.newInstance(user);
        editProfileDialog.show(ft, "edit_profile_dialog");
    }


    public void ExerciseList(View view) {
        // Obtén el contexto actual
        Context context = requireContext();

        // Crea un intent para iniciar la actividad ExerciseList
        Intent intent = new Intent(context, ExerciseList.class);

        // Inicia la actividad
        startActivity(intent);
    }
    public void DietExerciseActivity(View view) {
        // Obtén el contexto actual
        Context context = requireContext();

        // Crea un intent para iniciar la actividad ExerciseList
        Intent intent = new Intent(context, ExerciseList.class);

        // Inicia la actividad
        startActivity(intent);
    }

}