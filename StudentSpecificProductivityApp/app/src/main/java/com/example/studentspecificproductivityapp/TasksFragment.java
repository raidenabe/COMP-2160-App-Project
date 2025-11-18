package com.example.studentspecificproductivityapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class TasksFragment extends Fragment {
    DatabaseHelper db;
    SessionManagement sessionManagement;
    int userId;

    EditText setTitle, setDesc;
    Button addBtn;
    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<TaskModel> tasks;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        setTitle = view.findViewById(R.id.setTitle);
        setDesc = view.findViewById(R.id.setDesc);
        addBtn = view.findViewById(R.id.addTaskBtn);
        recyclerView = view.findViewById(R.id.recyclerViewTasks);

        db = new DatabaseHelper(getContext());
        sessionManagement = new SessionManagement(getContext());
        userId = sessionManagement.getUserId();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadTasks();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = setTitle.getText().toString().trim();
                String desc = setDesc.getText().toString().trim();

                if(title.isEmpty()){
                    return;
                }
                boolean success = db.addTask(title,desc,userId);
                if(success){
                    setTitle.setText("");
                    setDesc.setText("");
                    loadTasks();
                }else{
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void loadTasks(){

        adapter = new TaskAdapter(db.getAllTasks(userId), new TaskAdapter.Callback() {
            @Override
            public void onDelete(TaskModel task) {
                db.deleteTask(task.getId());
                loadTasks();
            }

            @Override
            public void onToggleComplete(TaskModel task, boolean completed) {
                db.setTaskCompleted(task.getId(),completed);
            }
        });

        recyclerView.setAdapter(adapter);
    }
}