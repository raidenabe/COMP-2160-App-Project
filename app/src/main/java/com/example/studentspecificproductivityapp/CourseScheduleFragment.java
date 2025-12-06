package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class CourseScheduleFragment extends Fragment implements CourseAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    ImageButton addCourseButton;
    ArrayList<CourseModel> courseList;
    DatabaseHelper db;
    CourseAdapter adapter;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_schedule, container, false);

        // Bind views
        recyclerView = view.findViewById(R.id.courseScheduleRecyclerView);
        addCourseButton = view.findViewById(R.id.courseScheduleAddButton);

        db = new DatabaseHelper(getContext());

        SessionManagement sessionManagement = new SessionManagement(getContext());
        userId = sessionManagement.getUserId();

        courseList = db.getAllCoursesForUser(userId);


        // CourseModel courseModel = new CourseModel(userId, "COMP 2160", "Mobile Application", "Monday, Wednesday", "11:00 to 12:00");
        // courseList.add(courseModel);

        // Alert Dialog that allows user to add a course to the schedule
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            Context context = getContext();
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Course to Schedule");

                final EditText inputCourseCode = new EditText(context);
                inputCourseCode.setHint("Course Code");

                final EditText inputCourseName = new EditText(context);
                inputCourseName.setHint("Course Name");

                final EditText inputCourseDays = new EditText(context);
                inputCourseDays.setHint("Days of the Week of Course");

                final EditText inputCourseHours = new EditText(context);
                inputCourseHours.setHint("Course Hours");


                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(inputCourseCode);
                linearLayout.addView(inputCourseName);
                linearLayout.addView(inputCourseDays);
                linearLayout.addView(inputCourseHours);
                linearLayout.setPadding(25, 25, 25, 25);
                builder.setView(linearLayout);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String courseCode = inputCourseCode.getText().toString().trim();
                        String courseName = inputCourseName.getText().toString().trim();
                        String courseDays = inputCourseDays.getText().toString().trim();
                        String courseHours = inputCourseHours.getText().toString().trim();

                        if (db.isCourseCodeUsed(sessionManagement.getUserId(), courseCode))
                        {
                            Toast.makeText(getContext(), "Course Code needs to be unique. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            CourseModel course = new CourseModel(userId, courseCode, courseName, courseDays, courseHours);
                            db.addCourse(course);
                            courseList.add(course);
                            adapter.notifyDataSetChanged();
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

        adapter = new CourseAdapter(courseList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onItemClick(int position) {
        CourseModel course = courseList.get(position);

        // Deletes course from database
        boolean success = db.deleteCourse(userId, course.getCourseCode());

        // Removes course from course list and notifies adapter
        if (success && position >= 0 && position < courseList.size()) {
            courseList.remove(position);
            adapter.notifyItemRemoved(position);
        }
    }

}