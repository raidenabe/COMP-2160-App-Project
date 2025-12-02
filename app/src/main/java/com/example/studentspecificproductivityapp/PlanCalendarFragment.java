package com.example.studentspecificproductivityapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class PlanCalendarFragment extends Fragment {
    CalendarView calendarView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String selectedDate = (month + 1) + "/" + dayOfMonth + "/" + year;

                Intent intent = new Intent(getContext(), PlanDailyActivity.class);
                intent.putExtra("Date", selectedDate);
                startActivity(intent);
            }
        });
        return view;
    }
}