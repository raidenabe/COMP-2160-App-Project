package com.example.studentspecificproductivityapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class PlanDailyActivity extends AppCompatActivity {
    private RecyclerView dailyPlanRecyclerView;
    private TextView dateTextView;
    private ImageButton addEventButton, goBackButton;
    private ArrayList<PlanEventModel> eventList;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plan_daily);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dailyPlanRecyclerView = findViewById(R.id.dailyPlanRecyclerView);
        dateTextView = findViewById(R.id.dateTextView);
        addEventButton = findViewById(R.id.addEventButton);
        goBackButton = findViewById(R.id.goBackButton);

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");

        dateTextView.setText(date);

        // Set event list
        eventList = new ArrayList<>();

        for (int i = 0; i < 24; i++)
        {
            String time = String.format(Locale.getDefault(), "%02d:00", i);
            PlanEventModel event = new PlanEventModel("", "", time);
            eventList.add(event);
        }

        PlanEventAdapter adapter = new PlanEventAdapter(eventList);
        dailyPlanRecyclerView.setAdapter(adapter);
        dailyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlanDailyActivity.this, PlanCalendarFragment.class));
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanDailyActivity.this);
                builder.setTitle("Set New Event");

                final EditText inputName = new EditText(PlanDailyActivity.this);
                inputName.setHint("Event Name");

                final EditText inputDesc = new EditText(PlanDailyActivity.this);
                inputDesc.setHint("Event Description");

                final TimePicker inputTime = new TimePicker(PlanDailyActivity.this);
                inputTime.setIs24HourView(true);


                LinearLayout linearLayout = new LinearLayout(PlanDailyActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(inputName);
                linearLayout.addView(inputDesc);
                linearLayout.addView(inputTime);
                builder.setView(linearLayout);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = inputName.getText().toString();
                        String desc = inputDesc.getText().toString();
                        String time = String.format(Locale.getDefault(), "%02d:00", inputTime.getHour());

                        for (int num = 0; num < eventList.size(); num++)
                        {
                            PlanEventModel event = eventList.get(num);
                            String eventTime = event.getTime();

                            if (eventTime.equals(time))
                            {
                                event.setName(name);
                                event.setDescription(desc);
                                adapter.notifyItemChanged(num);


                            }
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();

            }
        });

    }
}