package com.example.studentspecificproductivityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    Button courseScheduleButton, planButton, studyButton, sleepTrackerButton, toDoListButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Binds views
        courseScheduleButton = view.findViewById(R.id.courseScheduleButton);
        planButton = view.findViewById(R.id.weeklyPlanButton);
        sleepTrackerButton = view.findViewById(R.id.sleepTrackerButton);
        toDoListButton = view.findViewById(R.id.toDoListButton);
        studyButton = view.findViewById(R.id.studyButton);

        // Sets on click listeners to navigate to respective fragments

        courseScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(3,false);
            }
        });

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(4,false);
            }
        });

        studyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(5,false);
            }
        });

        sleepTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(6,false);
            }
        });

        toDoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getViewPager().setCurrentItem(7,false);
            }
        });
        return view;
    }
}