package com.ispc.gymapp.views.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gymapp.R;
import com.ispc.gymapp.model.User;

public class EditProfileActivity extends DialogFragment {
    private User user;
    private EditText editName;
    private EditText editWeight;
    private EditText editHeight;
    // private EditEmail editEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_modal, container, false);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Configura el contenido del modal de edición
        editName = view.findViewById(R.id.editName);
        editWeight = view.findViewById(R.id.editWeight);
        editHeight = view.findViewById(R.id.editHeight);
        // editEmail = view.findViewById(R.id.editEmail);

        // Retrieve the user object from fragment arguments
        Bundle args = getArguments();
        if (args != null) {
            user = args.getParcelable("user");
        }

        // Verify that the User object is not null
        if (user != null) {
            editName.setText(user.getName());
            editWeight.setText(String.valueOf(user.getWeight()));
            editHeight.setText(String.valueOf(user.getHeight()));
        }

        Button saveChangesButton = view.findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Procesa y guarda los cambios en el perfil
                sendUpdatedDataToProfileFragment();

                // Cierra el modal de edición
                dismiss();
            }
        });
        return view;
    }

    private void sendUpdatedDataToProfileFragment() {
        // Obtén el nuevo nombre, peso y altura
        String newName = editName.getText().toString();
        double newWeight = Double.parseDouble(editWeight.getText().toString());
        int newHeight = Integer.parseInt(editHeight.getText().toString());

        // Crea un nuevo objeto User con los datos actualizados
        User updatedUser = new User();
        updatedUser.setName(newName);
        updatedUser.setWeight(newWeight);
        updatedUser.setHeight(newHeight);

        // Envía los datos de vuelta al fragmento anterior (ProfileFragment)
        getParentFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra("updatedUser", updatedUser));
    }

    public static EditProfileActivity newInstance(User user) {
        EditProfileActivity fragment = new EditProfileActivity();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }
}
