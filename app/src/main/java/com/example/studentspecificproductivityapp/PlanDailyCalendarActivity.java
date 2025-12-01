package com.example.studentspecificproductivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class PlanDailyCalendarActivity extends AppCompatActivity {
    Button addEventButton, previousDayButton, nextDayButton;
    TextView monthDayText, dayOfWeekText;
    ListView hourListView;
    LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan_daily_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addEventButton = findViewById(R.id.addEventButton);
        monthDayText = findViewById(R.id.monthDayTextView);
        dayOfWeekText = findViewById(R.id.dayOfWeekTextView);
        hourListView = findViewById(R.id.hourListView);
        selectedDate = LocalDate.now();

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanDailyCalendarActivity.this, PlanEventEditActivity.class));
            }
        });

        previousDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusDays(1);
                setDayView();
            }
        });

        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusDays(1);
                setDayView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
    }

    private void setDayView() {
        // Sets the title to proper day and year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        monthDayText.setText(selectedDate.format(formatter));

        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekText.setText(dayOfWeek);
    }
}