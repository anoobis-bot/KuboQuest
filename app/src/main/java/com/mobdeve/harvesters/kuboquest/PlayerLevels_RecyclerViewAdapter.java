package com.mobdeve.harvesters.kuboquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayerLevels_RecyclerViewAdapter extends RecyclerView.Adapter<PlayerLevels_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    int[] playerLevels, plantSprites;

    public PlayerLevels_RecyclerViewAdapter(Context context, int[] playerLevels, int[] plantSprites) {
        this.context = context;
        this.playerLevels = playerLevels;
        this.plantSprites = plantSprites;
    }

    @NonNull
    @Override
    public PlayerLevels_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        if (isEven(viewType))
        {
            view = inflater.inflate(R.layout.recycler_view_plant_levels_right, parent, false);
        }
        else
        {
            view = inflater.inflate(R.layout.recycler_view_plant_levels_left, parent, false);
        }

        return new PlayerLevels_RecyclerViewAdapter.MyViewHolder(view);
    }

    // Return the view type based on position or item level
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerLevels_RecyclerViewAdapter.MyViewHolder holder, int position) {
        if (isEven(position))
        {
            holder.txtLevelLeft.setText("Level " + playerLevels[position]);
            if (position == playerLevels.length - 1) {
                holder.imgPlantRight.setImageResource(R.drawable.sprite_plant_locked);
            }
            else {
                holder.imgPlantRight.setImageResource(plantSprites[position]);
            }
        }
        else
        {
            holder.txtLevelRight.setText("Level " + playerLevels[position]);
            if (position == playerLevels.length - 1) {
                holder.imgPlantLeft.setImageResource(R.drawable.sprite_plant_locked);
            }
            else {
                holder.imgPlantLeft.setImageResource(plantSprites[position]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return playerLevels.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtLevelLeft;
        TextView txtLevelRight;
        ImageView imgPlantLeft;
        ImageView imgPlantRight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLevelLeft = null;
            txtLevelRight = null;
            imgPlantLeft = null;
            imgPlantRight = null;

            txtLevelLeft = itemView.findViewById(R.id.txtTaskName);
            txtLevelRight = itemView.findViewById(R.id.txtLevelRight);
            imgPlantLeft = itemView.findViewById(R.id.imgPlantLeft);
            imgPlantRight = itemView.findViewById(R.id.imgPlantRight);

        }
    }

    private boolean isEven(int num) {
        return num % 2 == 0;
    }
}
