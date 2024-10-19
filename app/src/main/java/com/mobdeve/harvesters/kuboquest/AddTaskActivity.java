package com.mobdeve.harvesters.kuboquest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class AddTaskActivity extends AppCompatActivity {

    private TextView addDateTextView;
    private Button createTaskBtn;
    private Spinner frequencySpinner;
    private Spinner difficultySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
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

        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, frequencyChoices) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the default view
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view;
                textView.setTextSize(11);
                return view;
            }
        };

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyChoices) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the default view
                View view = super.getView(position, convertView, parent);

                TextView textView = (TextView) view;
                textView.setTextSize(11);
                return view;
            }
        };

        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        frequencySpinner.setAdapter(frequencyAdapter);
        difficultySpinner.setAdapter(difficultyAdapter);


        addDateTextView = findViewById(R.id.addDateTextView);
        createTaskBtn = findViewById(R.id.createTaskBtn);

        addDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        createTaskBtn.setOnClickListener(v -> createTask());
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
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDay  + "/" + selectedYear;
                    addDateTextView.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void createTask() {
        Intent result_intent = new Intent();
        setResult(1, result_intent);
        finish();
    }
}