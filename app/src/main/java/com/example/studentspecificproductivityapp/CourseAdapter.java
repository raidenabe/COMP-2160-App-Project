package com.example.studentspecificproductivityapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private ArrayList<CourseModel> courseList = new ArrayList<CourseModel>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public CourseAdapter(ArrayList<CourseModel> courseList, OnItemClickListener onItemClickListener) {
        this.courseList = courseList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseModel course = courseList.get(position);
        holder.courseID.setText(course.getCourseCode());
        holder.courseName.setText(course.getCourseName());
        holder.courseDays.setText(course.getCourseDaysOfTheWeek());
        holder.courseHours.setText(course.getCourseHours());

        holder.courseDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseID, courseName, courseDays, courseHours;
        ImageButton courseDeleteButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseID = itemView.findViewById(R.id.courseIDTextView);
            courseName = itemView.findViewById(R.id.courseNameTextView);
            courseDays = itemView.findViewById(R.id.courseDaysTextView);
            courseHours = itemView.findViewById(R.id.courseHoursTextView);
            courseDeleteButton = itemView.findViewById(R.id.courseScheduleDeleteButton);
        }
    }
}
