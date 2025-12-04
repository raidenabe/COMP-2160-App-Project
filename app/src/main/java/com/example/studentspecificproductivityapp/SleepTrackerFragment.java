package com.example.studentspecificproductivityapp;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SleepTrackerFragment extends Fragment {

    Button setSleepBtn, setWakeBtn, saveBtn;
    RecyclerView recyclerView;
    SleepAdapter adapter;
    long[] sleepTime = {0};
    long[] wakeTime = {0};

    long duration = 0;
    DatabaseHelper db;
    SessionManagement sessionManagement;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_tracker, container, false);

        setSleepBtn = view.findViewById(R.id.setSleepBtn);
        setWakeBtn = view.findViewById(R.id.setWakeBtn);
        saveBtn = view.findViewById(R.id.saveSleepBtn);
        recyclerView = view.findViewById(R.id.recyclerViewSleep);
        sessionManagement = new SessionManagement(getContext());
        userId = sessionManagement.getUserId();

        db = new DatabaseHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadSleepRecords();

        setSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(sleepTime);
            }
        });
        setWakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(wakeTime);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration = wakeTime[0] - sleepTime[0];
                if(duration<0){
                    duration+= 24*60*60*1000;
                }
                boolean success = db.addSleepRecord(sleepTime[0],wakeTime[0],duration,userId);
                Toast.makeText(getContext(),success ? "Sleep saved": "error" + duration,Toast.LENGTH_SHORT).show();
                loadSleepRecords();
            }
        });
        return view;
    }

    private void loadSleepRecords(){
        adapter = new SleepAdapter(db.getAllSleepRecords(userId), new SleepAdapter.Callback(){
            @Override
            public void onDelete(SleepModel sleep) {
                db.deleteSleepRecord(sleep.getId());
                loadSleepRecords();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void pickTime(long[] time){
        Calendar calendar  = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog= new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                time[0] = calendar.getTimeInMillis();
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
}