package com.example.studentspecificproductivityapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>
{
    private final ArrayList<String> daysOfMonth;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates view from calendar cell
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() / 8);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }


    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfMonth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.dayCellTextView);
        }
    }
}
