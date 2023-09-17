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
 * Use the {@link GoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalFragment extends Fragment {


    private static final String ARG_CSTEP_NUMBER = "currentStep";
    private static final String ARG_TSTEP_NUMBER = "totalSteps";

    private int mParam1;
    private int mParam2;

    public GoalFragment() {
    }


    public static GoalFragment newInstance(int currentStep, int totalSteps) {
        GoalFragment fragment = new GoalFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_goal_fragment, container, false);



        Bundle args = getArguments();
        if (args != null) {
            mParam1 = args.getInt(ARG_CSTEP_NUMBER);
            mParam2 = args.getInt(ARG_TSTEP_NUMBER);

            TextView stepTextView = rootView.findViewById(R.id.stepTextView);
            stepTextView.setText("Paso " + mParam1 + " de " + mParam2);
        }
        return rootView;
    }
}