package com.example.studentspecificproductivityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlanFragment extends Fragment implements PlanCalendarAdapter.OnItemClickListener {
    Button previousMonthButton, nextMonthButton;
    TextView monthYearText;
    RecyclerView calendarRecyclerView;
    LocalDate selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        // View Binding
        monthYearText = view.findViewById(R.id.monthYearTextView);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        previousMonthButton = view.findViewById(R.id.previousMonthButton);
        nextMonthButton = view.findViewById(R.id.nextMonthButton);

        // Sets selected date to current date
        selectedDate = LocalDate.now();

        // Creates the initial month view with current date
        setMonthView();

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });

        return view;
    }

    private void setMonthView() {
        // Sets the title to proper month and year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        monthYearText.setText(selectedDate.format(formatter));

        ArrayList<String> daysInMonthArrayList = daysInMonthArrayList(selectedDate);

        PlanCalendarAdapter adapter = new PlanCalendarAdapter(getContext(), daysInMonthArrayList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(adapter);
    }

    private ArrayList<String> daysInMonthArrayList(LocalDate localDate)
    {
        ArrayList<String> daysInMonthArrayList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);

        // Gets number of days in month
        int daysInMonth = yearMonth.lengthOfMonth();

        // Gets first day of the month
        LocalDate firstOfTheMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfTheMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 38; i++)
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArrayList.add("");
            }
            else
            {
                daysInMonthArrayList.add(String.valueOf(i - dayOfWeek));
            }
        }

        return daysInMonthArrayList;
    }

    @Override
    public void onItemClick(String dayText, int position) {
        if (!dayText.isEmpty())
        {

            Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
        }

    }
}