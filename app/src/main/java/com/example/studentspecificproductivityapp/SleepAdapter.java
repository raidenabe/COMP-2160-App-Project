package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.ViewHolder> {

    public interface Callback{
        void onDelete(SleepModel task);
    }
    private ArrayList<SleepModel> sleepList;

    Callback cb;
    // Constructor to get context and data
    public SleepAdapter(ArrayList<SleepModel> sleepList, Callback cb) {
        this.sleepList = sleepList;
        this.cb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sleep_record, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SleepModel model = sleepList.get(position);
        SimpleDateFormat sdf =  new SimpleDateFormat("hh:mm a", Locale.getDefault());
        holder.sleepTime.setText("Sleep: "+ sdf.format(new Date(model.getSleepTime())));
        holder.wakeTime.setText("Wake: "+ sdf.format(new Date(model.getWakeTime())));
        long hours = model.getDuration()/(1000*60*60);
        long minute = (model.getDuration()/(1000*60))%60;
        holder.durationTime.setText("Duration: "+ hours +"h "+ minute+"m");

        holder.deleteBtn.setOnClickListener(v ->{
            if(cb!=null){
                cb.onDelete(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sleepList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sleepTime, wakeTime, durationTime;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            sleepTime = itemView.findViewById(R.id.sleepTime);
            wakeTime = itemView.findViewById(R.id.wakeTime);
            durationTime = itemView.findViewById(R.id.durationTime);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}