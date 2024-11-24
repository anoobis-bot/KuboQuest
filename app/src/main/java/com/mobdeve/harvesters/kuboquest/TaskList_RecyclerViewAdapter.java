package com.mobdeve.harvesters.kuboquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskList_RecyclerViewAdapter extends RecyclerView.Adapter<TaskList_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> taskModelList;
    ArrayList<TaskModel> filteredTaskList;
    String filterFreq;

    public TaskList_RecyclerViewAdapter(Context context, ArrayList<TaskModel> taskModelList, String filterFreq) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.filteredTaskList = new ArrayList<>();
        this.filterFreq = filterFreq;
    }

    public void changeFilter(String filterFreq) {
        this.filterFreq = filterFreq;

        filteredTaskList.clear();
        for (TaskModel task : this.taskModelList) {
            if (task.getTaskFrequency().equals(this.filterFreq)) {
                filteredTaskList.add(task);
            }
        }

        notifyDataSetChanged();
    }

    public void addTask(TaskModel task) {
        if (task.getTaskFrequency().equals(this.filterFreq)) {
            filteredTaskList.add(task);
            notifyItemChanged(filteredTaskList.size() - 1);
        }

        taskModelList.add(task);
    }

    @NonNull
    @Override
    public TaskList_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_tasks, parent, false);

        return new TaskList_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskList_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txtTaskName.setText(filteredTaskList.get(position).getTaskName());
        holder.txtTaskDesc.setText(filteredTaskList.get(position).getTaskDescription());
        holder.checkBox.setChecked(filteredTaskList.get(position).getIsDone());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredTaskList.get(holder.getAdapterPosition()).invertIsDone();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredTaskList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTaskName;
        TextView txtTaskDesc;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtTaskDesc = itemView.findViewById(R.id.txtTaskDesc);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
