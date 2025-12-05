package com.example.studentspecificproductivityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment {

    TextView sleepRecord, studyRecord, completedTasks, plannedEvents, totalTasks;
    DatabaseHelper db;
    SessionManagement session;
    int userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sleepRecord = view.findViewById(R.id.sleepRecord);
        studyRecord = view.findViewById(R.id.studyRecord);
        completedTasks = view.findViewById(R.id.completedTasks);
        plannedEvents = view.findViewById(R.id.plannedEvent);
        totalTasks = view.findViewById(R.id.totalTasks);

        db = new DatabaseHelper(getContext());
        session = new SessionManagement(getContext());
        userId = session.getUserId();

        loadStats();
        return view;
    }

    public void onResume(){
        super.onResume();
        loadStats();
    }

    public void loadStats(){
        long sleepDuration = db.getTotalSleep(userId);
        long studyDuration = db.getTotalStudyDuration(userId);
        long completedTasksLong = db.getCompletedTasksCount(userId);
        long eventsPlanning = db.getPlannedEventsCount(userId);
        long totalTasksLong = db.getTaskCount(userId);

        sleepRecord.setText("Total Sleep Time: "+formatHour(sleepDuration));
        studyRecord.setText("Total Study Time: "+ formatHour(studyDuration));
        totalTasks.setText("Total Tasks: "+totalTasksLong);
        completedTasks.setText("Tasks Completed: "+completedTasksLong);
        plannedEvents.setText("Planned Events: "+ eventsPlanning);
    }
    public String formatHour(long millis){
        long hours = millis/(1000*60*60);
        long mins = (millis/(1000*60))%60;

        return hours + "h "+ mins +"m";
    }
}