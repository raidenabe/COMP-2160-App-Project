package com.example.studentspecificproductivityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    Button planButton, studyButton, sleepTrackerButton, toDoListButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        planButton = view.findViewById(R.id.weeklyPlanButton);
        sleepTrackerButton = view.findViewById(R.id.sleepTrackerButton);
        toDoListButton = view.findViewById(R.id.toDoListButton);
        studyButton = view.findViewById(R.id.studyButton);

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(3);
            }
        });

        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(4);
            }
        });

        sleepTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(5);
            }
        });

        toDoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(6);
            }
        });
        return view;
    }
}