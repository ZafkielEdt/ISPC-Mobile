package com.ispc.gymapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.viewmodel.RegisterViewModel;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CSTEP_NUMBER = "currentStep";
    private static final String ARG_TSTEP_NUMBER = "totalSteps";

    // TODO: Rename and change types of parameters
    private RadioButton rbMen;
    private RadioButton rbWomen;
    private RegisterViewModel viewModel;

    public GenderFragment() {
        // Required empty public constructor
    }


    public static GenderFragment newInstance(int currentStep, int totalSteps) {
        GenderFragment fragment = new GenderFragment();
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
            int mParam1 = getArguments().getInt(ARG_CSTEP_NUMBER);
            int mParam2 = getArguments().getInt(ARG_CSTEP_NUMBER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_genre, container, false);
        RadioGroup imageRadioGroup = rootView.findViewById(R.id.radioGroup);
        rbMen = rootView.findViewById(R.id.rbMen);
        rbWomen = rootView.findViewById(R.id.rbWomen);
        viewModel  = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
        imageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                           if (checkedId == R.id.rbMen) {
                                                               viewModel.setInputData("gender","M");
                                                           } else if (checkedId == R.id.rbWomen) {
                                                               viewModel.setInputData("gender","F");
                                                           }
                                                       }
        });
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