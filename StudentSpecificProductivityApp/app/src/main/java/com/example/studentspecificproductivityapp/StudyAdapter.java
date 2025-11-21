package com.example.studentspecificproductivityapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {
    public interface Callback {
        void onDelete(StudySessionModel session);
    }

    private ArrayList<StudySessionModel> studyList;
    Callback cb;

    public StudyAdapter(ArrayList<StudySessionModel> studyList, Callback cb) {
        this.studyList = studyList;
        this.cb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudySessionModel model = studyList.get(position);
        holder.studySubject.setText(model.getSubject());

        long hours = model.getDuration() / 3600000;
        long minutes = (model.getDuration() % 3600000) / 60000;
        holder.studyDuration.setText(String.format(Locale.getDefault(), "Duration: %dh %dm", hours, minutes));

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
        holder.studyDate.setText(sdf.format(new Date(model.getStartTime())));

        if (model.getNotes() != null && !model.getNotes().isEmpty()) {
            holder.studyNotes.setText("Notes: " + model.getNotes());
            holder.studyNotes.setVisibility(View.VISIBLE);
        } else {
            holder.studyNotes.setVisibility(View.GONE);
        }

        holder.deleteBtn.setOnClickListener(v -> {
            if (cb != null) {
                cb.onDelete(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studySubject, studyDuration, studyDate, studyNotes;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            studySubject = itemView.findViewById(R.id.studySubject);
            studyDuration = itemView.findViewById(R.id.studyDuration);
            studyDate = itemView.findViewById(R.id.studyDate);
            studyNotes = itemView.findViewById(R.id.studyNotes);
            deleteBtn = itemView.findViewById(R.id.deleteStudyBtn);
        }
    }
}
