package com.example.studentspecificproductivityapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;

public class PlanEventEditActivity extends AppCompatActivity {
    EditText eventNameEdit;
    TextView eventDateText, eventTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan_event_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eventNameEdit = findViewById(R.id.eventNameEditText);
        eventDateText = findViewById(R.id.eventDateTextView);
        eventTimeText = findViewById(R.id.eventTimeTextView);

        LocalDate selectedDate = PlanDailyCalendarActivity.selectedDate;

        eventDateText.setText("Date: " + );
    }
}