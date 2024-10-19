package com.mobdeve.harvesters.kuboquest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {

    private TextView addDateTextView;
    private Button editTaskBtn;
    private Button deleteTaskBtn;
    private Spinner frequencySpinner;
    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequencySpinner = findViewById(R.id.frequencySpinner);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        ArrayList<String> frequencyChoices = new ArrayList<>();
        frequencyChoices.add("Daily");
        frequencyChoices.add("Weekly");
        frequencyChoices.add("Yearly");
        ArrayList<String> difficultyChoices = new ArrayList<>();
        difficultyChoices.add("Easy");
        difficultyChoices.add("Medium");
        difficultyChoices.add("Hard");
        difficultyChoices.add("Extreme");

        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, frequencyChoices);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficultyChoices);

        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frequencySpinner.setAdapter(frequencyAdapter);
        difficultySpinner.setAdapter(difficultyAdapter);

        addDateTextView = findViewById(R.id.addDateTextView);
        editTaskBtn = findViewById(R.id.editTaskBtn);
        deleteTaskBtn = findViewById(R.id.deleteTaskBtn);

        addDateTextView.setOnClickListener(v -> showDatePickerDialog());
        editTaskBtn.setOnClickListener(v -> editTask());
        deleteTaskBtn.setOnClickListener(v -> deleteTask());
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date and set it to the TextView
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    addDateTextView.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void editTask() {
        Intent result_intent = new Intent();
        setResult(2, result_intent);
        finish();
    }

    private void deleteTask() {
        Intent result_intent = new Intent();
        setResult(0, result_intent);
        finish();
    }
}