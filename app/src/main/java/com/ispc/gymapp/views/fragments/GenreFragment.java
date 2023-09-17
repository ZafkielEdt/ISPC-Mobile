package com.ispc.gymapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ispc.gymapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CSTEP_NUMBER = "currentStep";
    private static final String ARG_TSTEP_NUMBER = "totalSteps";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;

    public GenreFragment() {
        // Required empty public constructor
    }


    public static GenreFragment newInstance(int currentStep, int totalSteps) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CSTEP_NUMBER, currentStep);
        args.putInt(ARG_TSTEP_NUMBER, totalSteps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_CSTEP_NUMBER);
            mParam2 = getArguments().getInt(ARG_CSTEP_NUMBER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_genre, container, false);



        Bundle args = getArguments();
        if (args != null) {
            int currentStep = args.getInt(ARG_CSTEP_NUMBER);
            int totalSteps = args.getInt(ARG_TSTEP_NUMBER);

            TextView stepTextView = rootView.findViewById(R.id.stepTextView);
            stepTextView.setText("Paso " + currentStep + " de " + totalSteps);
        }
        return rootView;
    }
}