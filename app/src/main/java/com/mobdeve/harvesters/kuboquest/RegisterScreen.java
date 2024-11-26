package com.mobdeve.harvesters.kuboquest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    EditText confirmPassword;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    CollectionReference usersRef = db.collection(FireStoreReferences.USER_COLLECTION);
    Map<String, Object> userData = new HashMap<>();
    boolean result;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), TaskList.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        Button btnProcess = findViewById(R.id.btnProceed);
        TextView txtSignIn = findViewById(R.id.signinTxtLink);
        username = findViewById(R.id.etUserName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etPasswordConfirm);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(username.getText())) {
                    Toast.makeText(RegisterScreen.this, "Missing username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(RegisterScreen.this, "Missing email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(RegisterScreen.this, "Missing password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword.getText())) {
                    Toast.makeText(RegisterScreen.this, "Missing confirm password.", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registered successfully! Write to FireStore and go to log in page.

                                    Toast.makeText(RegisterScreen.this, "Success.", Toast.LENGTH_SHORT).show();

                                    currentUser = mAuth.getCurrentUser();

                                    if (currentUser != null) {
                                        String uid = currentUser.getUid();

                                        userData.put(FireStoreReferences.USERNAME_FIELD, username.getText().toString());
                                        userData.put(FireStoreReferences.TOTALENERGYSAVED_FIELD, 0);
                                        userData.put(FireStoreReferences.ACTIVEPLANT_FIELD, 0);

                                        db.collection(FireStoreReferences.USER_COLLECTION).document(uid).set(userData);
                                    }

                                    Intent intent = new Intent(getApplicationContext(), FirstSeed.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(RegisterScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean doesUsernameExist() {


        Query query = usersRef.whereEqualTo(
                FireStoreReferences.USERNAME_FIELD,
                username.getText().toString()
        );


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    result = !task.getResult().isEmpty();
                }

                else {
                    Toast.makeText(RegisterScreen.this, "Unable to check database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return result;
    }
}