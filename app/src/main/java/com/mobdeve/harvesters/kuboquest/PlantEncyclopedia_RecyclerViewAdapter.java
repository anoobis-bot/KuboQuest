package com.mobdeve.harvesters.kuboquest;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantEncyclopedia_RecyclerViewAdapter extends RecyclerView.Adapter<PlantEncyclopedia_RecyclerViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<PlantModel> plantList;

    public PlantEncyclopedia_RecyclerViewAdapter(Context context, ArrayList<PlantModel> plantList) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantEncyclopedia_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_plant_encyclopedia, parent, false);

        return new PlantEncyclopedia_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantEncyclopedia_RecyclerViewAdapter.MyViewHolder holder, int position) {
        if (plantList.get(position*3).isLocked())
        {
            holder.plantImg1.setImageResource(R.drawable.sprite_plant_locked);
        }
        else {
            holder.plantImg1.setImageResource(plantList.get(position*3).getIconResource());
        }
        holder.plantName1.setText(plantList.get(position*3).getName());
        holder.plantBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlantDetails(plantList.get(holder.getAdapterPosition()*3).getName(), plantList.get(holder.getAdapterPosition()*3).getDescription(),
                        plantList.get(holder.getAdapterPosition()*3).getIconResource(), plantList.get(holder.getAdapterPosition()*3).isLocked(),
                        plantList.get(holder.getAdapterPosition()*3).getRequiredEnergy());
            }
        });

        if (position*3 + 1 < plantList.size())
        {
            if (plantList.get(position*3 + 1).isLocked())
            {
                holder.plantImg2.setImageResource(R.drawable.sprite_plant_locked);
            }
            else {
                holder.plantImg2.setImageResource(plantList.get(position*3 + 1).getIconResource());
            }
            holder.plantName2.setText(plantList.get(position*3 + 1).getName());
            holder.plantBox2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPlantDetails(plantList.get(holder.getAdapterPosition()*3 + 1).getName(), plantList.get(holder.getAdapterPosition()*3 + 1).getDescription(),
                            plantList.get(holder.getAdapterPosition()*3 + 1).getIconResource(), plantList.get(holder.getAdapterPosition()*3 + 1).isLocked(),
                            plantList.get(holder.getAdapterPosition()*3 + 1).getRequiredEnergy());
                }
            });
        }
        else {
            holder.plantBox2.setVisibility(View.INVISIBLE);
            holder.plantName2.setText("");
        }

        if (position*3 + 2 < plantList.size())
        {
            if (plantList.get(position*3 + 2).isLocked())
            {
                holder.plantImg3.setImageResource(R.drawable.sprite_plant_locked);
            }
            else {
                holder.plantImg3.setImageResource(plantList.get(position*3 + 1).getIconResource());
            }
            holder.plantName3.setText(plantList.get(position*3 + 2).getName());
            holder.plantBox3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPlantDetails(plantList.get(holder.getAdapterPosition()*3 + 2).getName(), plantList.get(holder.getAdapterPosition()*3 + 2).getDescription(),
                            plantList.get(holder.getAdapterPosition()*3 + 2).getIconResource(), plantList.get(holder.getAdapterPosition()*3 + 2).isLocked(),
                            plantList.get(holder.getAdapterPosition()*3 + 2).getRequiredEnergy());
                }
            });
        }
        else {
            holder.plantBox3.setVisibility(View.INVISIBLE);
            holder.plantName3.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return (plantList.size() + 2) / 3;
    }

    private void showPlantDetails(String plantName, String plantDesc, int plantImage, boolean isLocked, int requiredEnergy) {
        if (!isLocked)
        {
            Dialog plantDetailsDialog = new Dialog(context);

            plantDetailsDialog.setContentView(R.layout.activity_plant_details);

            ImageView plantImageDialog = plantDetailsDialog.findViewById(R.id.plant_image);
            TextView plantNameDialog = plantDetailsDialog.findViewById(R.id.plant_name);
            TextView plantDescDialog = plantDetailsDialog.findViewById(R.id.plant_description);

            plantImageDialog.setImageResource(plantImage); // Tomato image
            plantNameDialog.setText(plantName);  // Set the name
            plantDescDialog.setText(plantDesc);

            plantDetailsDialog.show();
        }
        else
        {
            // Create a custom dialog for locked plant details
            Dialog lockedPlantDetailsDialog = new Dialog(context);

            // Set the content view to the custom layout for locked plant details
            lockedPlantDetailsDialog.setContentView(R.layout.activity_plant_locked);

            // Set the plant name in the dialog
            TextView plantNameDialog = lockedPlantDetailsDialog.findViewById(R.id.plant_name);
            plantNameDialog.setText(plantName);  // Dynamically set the name

            // Set the required energy for unlocking the plant
            TextView requiredEnergyButton = lockedPlantDetailsDialog.findViewById(R.id.required_energy_button);
            requiredEnergyButton.setText("Required Energy: " + requiredEnergy);

            // Show the dialog
            lockedPlantDetailsDialog.show();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout plantBox1;
        ImageView plantImg1;
        TextView plantName1;

        FrameLayout plantBox2;
        ImageView plantImg2;
        TextView plantName2;

        FrameLayout plantBox3;
        ImageView plantImg3;
        TextView plantName3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            plantBox1 = itemView.findViewById(R.id.plantBox1);
            plantImg1 = itemView.findViewById(R.id.plantImg1);
            plantName1 = itemView.findViewById(R.id.plantName1);

            plantBox2 = itemView.findViewById(R.id.plantBox2);
            plantImg2 = itemView.findViewById(R.id.plantImg2);
            plantName2 = itemView.findViewById(R.id.plantName2);

            plantBox3 = itemView.findViewById(R.id.plantBox3);
            plantImg3 = itemView.findViewById(R.id.plantImg3);
            plantName3 = itemView.findViewById(R.id.plantName3);
        }
    }
}
