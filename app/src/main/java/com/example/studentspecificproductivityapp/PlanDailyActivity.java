package com.example.studentspecificproductivityapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class PlanDailyActivity extends AppCompatActivity implements PlanEventAdapter.OnItemLongClickListener{
    private RecyclerView dailyPlanRecyclerView;
    private TextView dateTextView;
    private ImageButton addEventButton, goBackButton;
    private ArrayList<PlanEventModel> eventList;
    SessionManagement sessionManagement;
    private DatabaseHelper db;
    PlanEventAdapter adapter;
    private int userId;


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

        sessionManagement = new SessionManagement(PlanDailyActivity.this);
        userId = sessionManagement.getUserId();

        db = new DatabaseHelper(PlanDailyActivity.this);

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");

        dateTextView.setText(date);

        // Set event list
        eventList = new ArrayList<>();

        // Gets an event list holding the added events from the user at that date
        ArrayList<PlanEventModel> loadEventsList = new ArrayList<>();
        loadEventsList = db.getAllPlannedEventsOnDateForUser(userId, date);

        // Creates an event list of blank events with times corresponding to the hour of the day.
        // If an event is found in the database at the same date and time from the user, it will
        // update the previous event list to include the added events.
        for (int i = 0; i < 24; i++) {
            String time = String.format(Locale.getDefault(), "%02d:00", i);
            PlanEventModel event;

            event = new PlanEventModel(userId, date, "", "", time);

            for (PlanEventModel loadedEvent: loadEventsList)
            {
                if (time.equals(loadedEvent.getTime()))
                {
                    event.setName(loadedEvent.getName());
                    event.setDescription(loadedEvent.getDescription());
                }
            }

            eventList.add(event);
        }

        // Creates an adapter with the event list and sets it to recycler view
        adapter = new PlanEventAdapter(eventList);
        adapter.setOnItemLongClickListener(this);
        dailyPlanRecyclerView.setAdapter(adapter);
        dailyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanDailyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Alert Dialog that allows user to add event to a specific date
        // Will save in Database and be able to be retrieved later
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

                        db.addPlanEvent(userId, date, name, desc, time);
                        adapter.notifyDataSetChanged();
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
    
    @Override
    public void onItemLongClick(int position) {
        PlanEventModel event = eventList.get(position);
        db.deletePlanEvent(userId, event.getDate(), event.getTime());
        adapter.notifyDataSetChanged();
    }


}