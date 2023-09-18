package com.ispc.gymapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.viewmodel.RegisterViewModel;


public class HeightFragment extends Fragment {


    private static final String ARG_CSTEP_NUMBER = "currentStep";
    private static final String ARG_TSTEP_NUMBER = "totalSteps";

    private int mParam1;
    private int mParam2;
    private NumberPicker numberPicker;
    private RegisterViewModel viewModel;
    public HeightFragment() {
        // Required empty public constructor
    }



    public static HeightFragment newInstance(int currentStep, int totalSteps) {
        HeightFragment fragment = new HeightFragment();
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
            mParam2 = getArguments().getInt(ARG_TSTEP_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_height, container, false);

        numberPicker = rootView.findViewById(R.id.numberPicker);
        fillNumberPicker(numberPicker);

        viewModel  = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                viewModel.setInputData("height",i1);
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


    private void fillNumberPicker(NumberPicker numberPicker){
        numberPicker.setMinValue(100);
        numberPicker.setMaxValue(230);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " cm";
            }
        });

    }
}