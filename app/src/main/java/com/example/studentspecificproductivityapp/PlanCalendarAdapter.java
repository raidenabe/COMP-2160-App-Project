package com.example.studentspecificproductivityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlanCalendarAdapter extends RecyclerView.Adapter<PlanCalendarAdapter.ViewHolder>
{
    Context context;
    private final ArrayList<String> daysOfMonth;
    private final OnItemClickListener onItemClickListener;
    public PlanCalendarAdapter(Context context, ArrayList<String> daysOfMonth, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates view from calendar cell
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_calendar_cell, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() / 8);

        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanCalendarAdapter.ViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }


    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(String dayText, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dayOfMonth;
        private final PlanCalendarAdapter.OnItemClickListener onItemClickListener;
        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.dayCellTextView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(String.valueOf(dayOfMonth.getText()), getBindingAdapterPosition());
        }
    }
}
