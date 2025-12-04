package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kotlinx.coroutines.scheduling.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public interface Callback{
        void onDelete(TaskModel task);
        void onToggleComplete(TaskModel task,boolean completed);
    }
    private ArrayList<TaskModel> taskList;
    Callback cb;

    // Constructor to get context and data
    public TaskAdapter(ArrayList<TaskModel> taskList, Callback cb) {
        this.taskList = taskList;
        this.cb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskModel model = taskList.get(position);
        holder.taskTitle.setText(model.getTitle());
        holder.taskDesc.setText(model.getDesc());

        holder.checkBoxComplete.setOnCheckedChangeListener(null);
        holder.checkBoxComplete.setChecked(model.isCompleted());
        if(model.isCompleted()){
            holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
            holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
        }


        holder.checkBoxComplete.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            model.setCompleted(isChecked);
            if(cb!= null){cb.onToggleComplete(model,isChecked);}
            if(isChecked){
                holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
            }

        }));

        holder.deleteBtn.setOnClickListener(v ->{
            if(cb!=null){
                cb.onDelete(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDesc;
        Button deleteBtn;
        CheckBox checkBoxComplete;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBoxComplete = itemView.findViewById(R.id.checkBoxComplete);
            taskDesc = itemView.findViewById(R.id.taskDesc);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}