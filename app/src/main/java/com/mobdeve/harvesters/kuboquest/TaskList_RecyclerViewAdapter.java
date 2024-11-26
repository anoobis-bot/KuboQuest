package com.mobdeve.harvesters.kuboquest;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskList_RecyclerViewAdapter extends RecyclerView.Adapter<TaskList_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> taskModelList;
    ArrayList<TaskModel> filteredTaskList;
    String filterFreq;

    ProgressBar progressXP;
    TextView textXP;
    ProgressBar progressWater;
    TextView textWater;
    TextView txtLevel;
    ImageView imgPlant;

    public TaskList_RecyclerViewAdapter(Context context, ArrayList<TaskModel> taskModelList, String filterFreq,
                                        ProgressBar progressBar, TextView textXP, ProgressBar progressWater, TextView textWater,
                                        TextView txtLevel, ImageView imgPlant) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.filteredTaskList = new ArrayList<>();
        this.filterFreq = filterFreq;

        this.progressXP = progressBar;
        this.textXP = textXP;
        this.progressWater = progressWater;
        this.textWater = textWater;
        this.txtLevel = txtLevel;
        this.imgPlant = imgPlant;
    }

    public void changeFilter(String filterFreq) {
        synchronized (this) {
            this.filterFreq = filterFreq;

            filteredTaskList.clear();
            for (TaskModel task : this.taskModelList) {
                if (task.getTaskFrequency().equals(this.filterFreq)) {
                    filteredTaskList.add(task);
                }
            }

            notifyDataSetChanged();
        }
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
        synchronized (this) {
            holder.txtTaskName.setText(filteredTaskList.get(position).getTaskName());
            holder.txtTaskDesc.setText(filteredTaskList.get(position).getTaskDescription());
            holder.checkBox.setChecked(filteredTaskList.get(position).getIsDone());

            if (filteredTaskList.get(position).getIsDone())
            {
                holder.checkBox.setEnabled(false);
                holder.txtTaskName.setPaintFlags(holder.txtTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtTaskDesc.setPaintFlags(holder.txtTaskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                holder.checkBox.setEnabled(true);
                holder.txtTaskName.setPaintFlags(0);
                holder.txtTaskDesc.setPaintFlags(0);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filteredTaskList.get(holder.getAdapterPosition()).invertIsDone();
                holder.checkBox.setEnabled(false);
                holder.txtTaskName.setPaintFlags(holder.txtTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.txtTaskDesc.setPaintFlags(holder.txtTaskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                PlayerModel player = PlayerModel.getInstance();
                String frequency = filteredTaskList.get(holder.getAdapterPosition()).getTaskFrequency();
                String difficulty = filteredTaskList.get(holder.getAdapterPosition()).getTaskDifficulty();
                player.getActivePlant().incrementXP(GainDebuffData.getXPGain(frequency, difficulty));
                player.incrementWater(GainDebuffData.getWaterGain(frequency, difficulty));

//                progress(GainDebuffData.getXPGain(frequency, difficulty));
                TaskList.updateProgressBar(progressXP, textXP, player.getActivePlant().getHarvestXP(),
                        GainDebuffData.getXPGain(frequency, difficulty), "XP");
                TaskList.updateProgressBar(progressWater, textWater, 100,
                        GainDebuffData.getWaterGain(frequency, difficulty), "/100");
                TaskList.updatePlantImgTxt(txtLevel, imgPlant);
            }
        });
    }

//    public void progress(int increment)
//    {
//        ;
//    }

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
