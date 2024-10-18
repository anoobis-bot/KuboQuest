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

    public TaskList_RecyclerViewAdapter(Context context, ArrayList<TaskModel> taskModelList) {
        this.context = context;
        this.taskModelList = taskModelList;
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
        holder.txtTaskName.setText(taskModelList.get(position).getTaskName());
        holder.txtTaskDesc.setText(taskModelList.get(position).getTaskDescription());
        holder.checkBox.setChecked(taskModelList.get(position).getIsDone());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskModelList.get(holder.getAdapterPosition()).invertIsDone();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
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

//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//
//                }
//            });
        }
    }
}
