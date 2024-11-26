package com.mobdeve.harvesters.kuboquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirstSeed extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    CollectionReference usersRef = db.collection(FireStoreReferences.USER_COLLECTION);
    CollectionReference plantsRef;
    Map<String, Object> plantOwned = new HashMap<>();

    TextView seedName;
    ImageView seedImg;
    Button btnProceed;

    GachaSystem gachaSystem = new GachaSystem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_seed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        PlantData.initialize(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        seedName = findViewById(R.id.seedName);
        seedImg = findViewById(R.id.seedImg);
        btnProceed = findViewById(R.id.btnProceed);
        PlantModel plant = gachaSystem.gachaPull();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TaskList.class);
                startActivity(intent);
                finish();
            }
        });

        seedName.setText(plant.getName());

        if (currentUser != null) {
            String uid = currentUser.getUid();

            plantOwned.put(FireStoreReferences.PLANTNAME_FIELD, plant.getName());
            plantOwned.put(FireStoreReferences.ISLOCKED_FIELD, false);
            plantOwned.put(FireStoreReferences.CURRENTXP_FIELD, 0);

            usersRef.document(uid).collection(FireStoreReferences.PLANT_COLLECTION)
                    .add(plantOwned)
                    .addOnSuccessListener(documentReference -> {
                        String plantDocumentId = documentReference.getId();

                        usersRef.document(uid)
                                .update(FireStoreReferences.ACTIVEPLANT_FIELD, plantDocumentId);
                    });
        }
    }


}