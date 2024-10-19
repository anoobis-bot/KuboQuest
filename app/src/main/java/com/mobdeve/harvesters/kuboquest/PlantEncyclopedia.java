package com.mobdeve.harvesters.kuboquest;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlantEncyclopedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_encyclopedia);

        // Find the tomato ImageView in activity_plant_encyclopedia.xml by its ID
        ImageView tomatoImage = findViewById(R.id.tomatoImage);

        // Set an onClickListener for the tomato image
        tomatoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlantDetails();
            }
        });

        ImageView limaBeansImage = findViewById(R.id.limaBeansImage);  // Assuming you have an ImageView with id limaBeansImage

        limaBeansImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Lima Beans", 10);
            }
        });

        ImageView winterMelonImage = findViewById(R.id.winterMelonImage);

        winterMelonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Winter Melon", 10);  // Example with energy requirement of 12
            }
        });

        ImageView peanutsImage = findViewById(R.id.peanutsImage);

        peanutsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Peanuts", 10);  // Example with energy requirement of 12
            }
        });

        ImageView mustardGreenImage = findViewById(R.id.mustardGreenImage);

        mustardGreenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Mustard Green", 10);  // Example with energy requirement of 12
            }
        });

        ImageView gingerImage = findViewById(R.id.gingerImage);

        gingerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Ginger", 10);  // Example with energy requirement of 12
            }
        });

        ImageView radishImage = findViewById(R.id.radishImage);

        radishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Radish", 10);  // Example with energy requirement of 12
            }
        });

        ImageView jicamaImage = findViewById(R.id.jicamaImage);

        jicamaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Jicama", 10);  // Example with energy requirement of 12
            }
        });

        ImageView onionImage = findViewById(R.id.onionImage);

        onionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Onion", 10);  // Example with energy requirement of 12
            }
        });

        ImageView eggplantImage = findViewById(R.id.eggplantImage);

        eggplantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Eggplant", 10);  // Example with energy requirement of 12
            }
        });

        ImageView squashImage = findViewById(R.id.squashImage);

        squashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Squash", 10);  // Example with energy requirement of 12
            }
        });

        ImageView garlicImage = findViewById(R.id.garlicImage);

        garlicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Garlic", 10);  // Example with energy requirement of 12
            }
        });

        ImageView hyacinthImage = findViewById(R.id.hyacinthImage);

        hyacinthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Hyacinth Beans", 10);  // Example with energy requirement of 12
            }
        });

        ImageView stringBeansImage = findViewById(R.id.stringBeansImage);

        stringBeansImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("String Beans", 10);  // Example with energy requirement of 12
            }
        });

        ImageView bottleGourdImage = findViewById(R.id.bottleGourdImage);

        bottleGourdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Bottle Gourd", 10);  // Example with energy requirement of 12
            }
        });

        ImageView sesameImage = findViewById(R.id.sesameImage);

        sesameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Sesame Plant", 10);  // Example with energy requirement of 12
            }
        });

        ImageView wingedBeansImage = findViewById(R.id.wingedBeansImage);

        wingedBeansImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Winged Beans", 10);  // Example with energy requirement of 12
            }
        });

        ImageView spongeGourdImage = findViewById(R.id.spongeGourdImage);

        spongeGourdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLockedPlantDetails("Sponge Gourd", 10);  // Example with energy requirement of 12
            }
        });


    }

    // Plant details popup
    private void showPlantDetails() {
        Dialog plantDetailsDialog = new Dialog(this);

        plantDetailsDialog.setContentView(R.layout.activity_plant_details);

        ImageView plantImage = plantDetailsDialog.findViewById(R.id.plant_image);
        TextView plantName = plantDetailsDialog.findViewById(R.id.plant_name);
        TextView plantDescription = plantDetailsDialog.findViewById(R.id.plant_description);

        plantImage.setImageResource(R.drawable.fruit_tomato); // Tomato image
        plantName.setText("Tomato");  // Set the name
        plantDescription.setText("Tomatoes are a great source of vitamins A, C, and K. They are easy to grow and require full sunlight for optimal growth.");

//        ImageView closeButton = plantDetailsDialog.findViewById(R.id.close_button);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plantDetailsDialog.dismiss();  // Close the dialog
//            }
//        });

        plantDetailsDialog.show();
    }

    private void showLockedPlantDetails(String plantName, int requiredEnergy) {
        // Create a custom dialog for locked plant details
        Dialog lockedPlantDetailsDialog = new Dialog(this);

        // Set the content view to the custom layout for locked plant details
        lockedPlantDetailsDialog.setContentView(R.layout.activity_plant_locked);

        // Set the plant name in the dialog
        TextView plantNameTextView = lockedPlantDetailsDialog.findViewById(R.id.plant_name);
        plantNameTextView.setText(plantName);  // Dynamically set the name

        // Set the required energy for unlocking the plant
        TextView requiredEnergyButton = lockedPlantDetailsDialog.findViewById(R.id.required_energy_button);
        requiredEnergyButton.setText("Required Energy: " + requiredEnergy);

//        // Handle the close button click
//        ImageView closeButton = lockedPlantDetailsDialog.findViewById(R.id.close_button);
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lockedPlantDetailsDialog.dismiss();  // Close the dialog
//            }
//        });

        // Show the dialog
        lockedPlantDetailsDialog.show();
    }
}
