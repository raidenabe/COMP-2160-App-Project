package com.example.studentspecificproductivityapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class PlanDailyActivity extends AppCompatActivity implements PlanEventAdapter.OnItemLongClickListener{
    private RecyclerView dailyPlanRecyclerView;
    private TextView dateTextView;
    private ImageButton addEventButton, goBackButton;
    private ArrayList<PlanEventModel> eventList;
    SessionManagement sessionManagement;
    private DatabaseHelper db;
    private PlanEventAdapter adapter;
    private int userId;
    private String date;


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

        // Binds views
        dailyPlanRecyclerView = findViewById(R.id.dailyPlanRecyclerView);
        dateTextView = findViewById(R.id.dateTextView);
        addEventButton = findViewById(R.id.addEventButton);
        goBackButton = findViewById(R.id.goBackButton);

        // Gets userId from shared preferences
        sessionManagement = new SessionManagement(PlanDailyActivity.this);
        userId = sessionManagement.getUserId();

        db = new DatabaseHelper(PlanDailyActivity.this);

        // Gets date from PlanCalendarFragment
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");

        dateTextView.setText(date);

        eventList = new ArrayList<>();

        // Gets an event list holding the added events from the user at that date
        ArrayList<PlanEventModel> loadEventsList = db.getAllPlannedEventsOnDateForUser(userId, date);

        // Sets the event list to new events with userId, time, and date, but placeholder values
        // for name and description
        loadEventList(loadEventsList);

        // Goes back to Main Activity
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
                linearLayout.setPadding(5, 5, 5, 5);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(inputName);
                linearLayout.addView(inputDesc);
                linearLayout.addView(inputTime);
                linearLayout.setPadding(25, 25, 25, 25);
                builder.setView(linearLayout);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = inputName.getText().toString();
                        String desc = inputDesc.getText().toString();
                        String time = String.format(Locale.getDefault(), "%02d:00", inputTime.getHour());

                        PlanEventModel event = new PlanEventModel(userId, date, name, desc, time);
                        int position = addToEventList(eventList, event);
                        db.addPlanEvent(event);

                        try {
                            adapter.notifyItemChanged(position);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
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

        // Creates an adapter with the event list and sets it to recycler view
        adapter = new PlanEventAdapter(eventList);
        adapter.setOnItemLongClickListener(this);
        dailyPlanRecyclerView.setAdapter(adapter);
        dailyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    
    @Override
    public void onItemLongClick(int position) {
        PlanEventModel event = eventList.get(position);
        db.deletePlanEvent(userId, event.getDate(), event.getTime());
        removeFromEventList(eventList, event);
        try {
            adapter.notifyItemChanged(position);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadEventList(ArrayList<PlanEventModel> EventList)
    {
        // Creates an event list of blank events with times corresponding to the hour of the day.
        // If an event is found in the database at the same date and time from the user, it will
        // update the previous event list to include the added events.
        for (int i = 0; i < 24; i++) {
            String time = String.format(Locale.getDefault(), "%02d:00", i);
            PlanEventModel event;

            event = new PlanEventModel(userId, date, "", "", time);

            for (PlanEventModel loadedEvent: EventList)
            {
                if (time.equals(loadedEvent.getTime()))
                {
                    event.setName(loadedEvent.getName());
                    event.setDescription(loadedEvent.getDescription());
                }
            }

            eventList.add(event);
        }
    }

    // Return adapter position in eventList and updates the name and description of event to the
    // name and description of the newly added event.
    public int addToEventList(ArrayList<PlanEventModel> eventList, PlanEventModel addedEvent)
    {
        int position = -1;
        for (int i = 0; i < eventList.size(); i++)
        {
            PlanEventModel event = eventList.get(i);
            if (event.getTime().equals(addedEvent.getTime()))
            {
                event.setName(addedEvent.getName());
                event.setDescription(addedEvent.getDescription());
                position = i;
            }
        }
        return position;
    }

    // Updates the name and description of event in the event list to be blank
    public void removeFromEventList(ArrayList<PlanEventModel> eventList, PlanEventModel removedEvent)
    {
        for (PlanEventModel event: eventList)
        {
            if (event.getTime().equals(removedEvent.getTime()))
            {
                event.setName("");
                event.setDescription("");
            }
        }
    }
}