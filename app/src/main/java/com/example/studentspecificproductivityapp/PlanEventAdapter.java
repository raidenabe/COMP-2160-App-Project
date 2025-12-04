package com.example.studentspecificproductivityapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlanEventAdapter extends RecyclerView.Adapter<PlanEventAdapter.ViewHolder> {
    private ArrayList<PlanEventModel> eventList = new ArrayList<PlanEventModel>();
    private OnItemLongClickListener onItemLongClickListener;

    public PlanEventAdapter(ArrayList<PlanEventModel> eventList) {
        this.eventList = eventList;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanEventModel planEvent = eventList.get(position);
        holder.hourTextView.setText(planEvent.getTime());
        holder.eventNameTextView.setText(planEvent.getName());
        holder.eventDescriptionTextView.setText(planEvent.getDescription());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener)
    {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourTextView, eventNameTextView, eventDescriptionTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.hourTextView);
            eventNameTextView = itemView.findViewById(R.id.eventNameTextView);
            eventDescriptionTextView = itemView.findViewById(R.id.eventDescriptionTextView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (onItemLongClickListener != null)
                        onItemLongClickListener.onItemLongClick(position);
                    return true;
                }
            });
        }
    }
}
