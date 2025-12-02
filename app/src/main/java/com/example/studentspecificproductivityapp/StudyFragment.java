package com.example.studentspecificproductivityapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudyFragment extends Fragment {
    DatabaseHelper db;
    SessionManagement sessionManagement;
    int userId;

    EditText subjectInput, notesInput;
    TextView timerDisplay;
    Button startBtn, stopBtn;
    RecyclerView recyclerView;
    StudyAdapter adapter;

    private boolean isTimerRunning = false;
    private long startTime = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);

        subjectInput = view.findViewById(R.id.subjectInput);
        notesInput = view.findViewById(R.id.notesInput);
        timerDisplay = view.findViewById(R.id.timerDisplay);
        startBtn = view.findViewById(R.id.startBtn);
        stopBtn = view.findViewById(R.id.stopBtn);
        recyclerView = view.findViewById(R.id.recyclerViewStudy);

        db = new DatabaseHelper(getContext());
        sessionManagement = new SessionManagement(getContext());
        userId = sessionManagement.getUserId();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadStudySessions();

        // Timer runnable to update the display
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                int seconds = (int) (elapsedTime / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;

                timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                timerHandler.postDelayed(this, 1000);
            }
        };

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectInput.getText().toString().trim();
                if (subject.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a subject", Toast.LENGTH_SHORT).show();
                    return;
                }

                isTimerRunning = true;
                startTime = System.currentTimeMillis();
                startBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                subjectInput.setEnabled(false);
                timerHandler.postDelayed(timerRunnable, 0);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    isTimerRunning = false;
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    timerHandler.removeCallbacks(timerRunnable);

                    String subject = subjectInput.getText().toString().trim();
                    String notes = notesInput.getText().toString().trim();

                    boolean success = db.addStudySession(subject, startTime, endTime, duration, notes, userId);
                    if (success) {
                        Toast.makeText(getContext(), "Study session saved!", Toast.LENGTH_SHORT).show();
                        subjectInput.setText("");
                        notesInput.setText("");
                        timerDisplay.setText("00:00:00");
                        loadStudySessions();
                    } else {
                        Toast.makeText(getContext(), "Error saving session", Toast.LENGTH_SHORT).show();
                    }

                    startBtn.setEnabled(true);
                    stopBtn.setEnabled(false);
                    subjectInput.setEnabled(true);
                }
            }
        });

        return view;
    }

    public void loadStudySessions() {
        adapter = new StudyAdapter(db.getAllStudySessions(userId), new StudyAdapter.Callback() {
            @Override
            public void onDelete(StudySessionModel session) {
                db.deleteStudySession(session.getId());
                loadStudySessions();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}
