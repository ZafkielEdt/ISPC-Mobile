package com.ispc.gymapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ispc.gymapp.R;
import com.ispc.gymapp.views.viewmodel.RegisterViewModel;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightFragment extends Fragment {

    private static final String ARG_CSTEP_NUMBER = "currentStep";
    private static final String ARG_TSTEP_NUMBER = "totalSteps";

    private RegisterViewModel viewModel;
    private NumberPicker kgPicker,grPicker;
    final int[] allowedValues = {0,100,200,300,400,500,600,700,800,900};
    private int kg;
    private int gr;

    public WeightFragment() {
        // Required empty public constructor
    }

    public static WeightFragment newInstance(int currentStep, int totalSteps) {
        WeightFragment fragment = new WeightFragment();
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
            int mParam2 = getArguments().getInt(ARG_TSTEP_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weight, container, false);
        viewModel  = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        fillNumberPicker(rootView);

        this.kgPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                kg = i1;
                updateCombinedValue();
            }
        });

        this.grPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                gr = allowedValues[i1];
                updateCombinedValue();
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
    private void fillNumberPicker(View rootView){
        this.kgPicker = rootView.findViewById(R.id.kgPicker);
        this.kgPicker.setMinValue(30);

        this.kgPicker.setMaxValue(530);
        this.kgPicker.setWrapSelectorWheel(true);
//        numberPicker2.setMinValue(100);
//        numberPicker2.setMaxValue(900);
        this.grPicker = rootView.findViewById(R.id.grPicker);

        this.grPicker.setMaxValue(allowedValues.length - 1);
        this.grPicker.setWrapSelectorWheel(true);
        this.grPicker.setDisplayedValues(new String[]{"0 gr","100 gr","200 gr","300 gr","400 gr","500 gr","600 gr","700 gr","800 gr","900 gr"});
        this.kgPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " kg";
            }
        });
        this.grPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " gr";
            }
        });

    }
    private void updateCombinedValue() {
        double combinedValue = kg + (gr / 1000.0);

        viewModel.setInputData("weight",combinedValue);
    }
}