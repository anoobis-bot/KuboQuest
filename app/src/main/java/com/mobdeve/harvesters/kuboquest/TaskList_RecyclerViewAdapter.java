package com.mobdeve.harvesters.kuboquest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TaskList_RecyclerViewAdapter extends RecyclerView.Adapter<TaskList_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<TaskModel> taskModelList;
    ArrayList<TaskModel> filteredTaskList;
    String filterFreq;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CollectionReference usersRef = db.collection(FireStoreReferences.USER_COLLECTION);

    ProgressBar progressXP;
    TextView textXP;
    ProgressBar progressWater;
    TextView textWater;
    TextView txtLevel;
    ImageView imgPlant;

    PlayerLevels_RecyclerViewAdapter adapter2;

    private ActivityResultLauncher<Intent> launcher;

    public TaskList_RecyclerViewAdapter(Context context, ArrayList<TaskModel> taskModelList, String filterFreq,
                                        ProgressBar progressBar, TextView textXP, ProgressBar progressWater, TextView textWater,
                                        TextView txtLevel, ImageView imgPlant, PlayerLevels_RecyclerViewAdapter adapater2,
                                        ActivityResultLauncher<Intent> launcher) {
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

        this.adapter2 = adapater2;

        this.launcher = launcher;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
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

    public void deleteTask(String taskID) {
        synchronized (this) {
            filteredTaskList.removeIf(task -> task.getTaskID().equals(taskID));
            taskModelList.removeIf(task -> task.getTaskID().equals(taskID));

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
                int origXP = player.getActivePlant().getCurrentXP();
                int origWater = player.getSoilWater();
                player.getActivePlant().incrementXP(GainDebuffData.getXPGain(frequency, difficulty));
                player.incrementWater(GainDebuffData.getWaterGain(frequency, difficulty));

                if (currentUser != null) {
                    String uid = currentUser.getUid();
                    String taskID = filteredTaskList.get(holder.getAdapterPosition()).getTaskID();

                    usersRef.document(uid)
                            .get()
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    String plantID = documentSnapshot.getString("activePlant");

                                    usersRef.document(uid)
                                            .collection(FireStoreReferences.PLANT_COLLECTION)
                                            .document(plantID)
                                            .update(FireStoreReferences.CURRENTXP_FIELD, player.getActivePlant().getCurrentXP());
                                }
                            });

                    usersRef.document(uid)
                            .collection(FireStoreReferences.TASK_COLLECTION)
                            .document(taskID)
                            .update(FireStoreReferences.TASKISDONE_FIELD, true);

                    usersRef.document(uid)
                            .update(FireStoreReferences.WATERLEVEL_FIELD, player.getSoilWater());
                }

//                progress(GainDebuffData.getXPGain(frequency, difficulty));
                TaskList.updateProgressBar(progressXP, textXP, origXP, player.getActivePlant().getHarvestXP(),
                        GainDebuffData.getXPGain(frequency, difficulty), "XP");
                TaskList.updateProgressBar(progressWater, textWater, origWater,100,
                        GainDebuffData.getWaterGain(frequency, difficulty), "/100");
                TaskList.updatePlantImgTxt(txtLevel, imgPlant);

                adapter2.notifyDataSetChanged();

                // new plant checker
                if (player.getActivePlant().getCurrentXP() >= player.getActivePlant().getHarvestXP()) {
                    Intent intent = new Intent(context, FirstSeed.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            }
        });

        holder.taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditTaskActivity.class);

                intent.putExtra("task_name", filteredTaskList.get(holder.getAdapterPosition()).getTaskName());
                intent.putExtra("task_desc", filteredTaskList.get(holder.getAdapterPosition()).getTaskDescription());
                intent.putExtra("task_freq", filteredTaskList.get(holder.getAdapterPosition()).getTaskFrequency());
                intent.putExtra("task_difficulty", filteredTaskList.get(holder.getAdapterPosition()).getTaskDifficulty());
                intent.putExtra("task_date", filteredTaskList.get(holder.getAdapterPosition()).getDateString());
                intent.putExtra("task_id", filteredTaskList.get(holder.getAdapterPosition()).getTaskID());
                launcher.launch(intent);
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

        CardView taskCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtTaskDesc = itemView.findViewById(R.id.txtTaskDesc);
            checkBox = itemView.findViewById(R.id.checkBox);

            taskCard = itemView.findViewById(R.id.taskCard);
        }
    }
}
