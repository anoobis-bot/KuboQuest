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

    private TextView txtDelTaskName;
    private TextView txtDelTaskDesc;
    private TextView txtDelTaskFreq;
    private TextView txtDelTaskDifficulty;


    private String taskID;
    private String name;

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

        Intent intent = getIntent();
        name = intent.getStringExtra("task_name");
        String desc = intent.getStringExtra("task_desc");
        String freq = intent.getStringExtra("task_freq");
        String difficulty = intent.getStringExtra("task_difficulty");
        String date = intent.getStringExtra("task_date");
        taskID = intent.getStringExtra("task_id");

        txtDelTaskName = findViewById(R.id.txtDelTaskName);
        txtDelTaskDesc = findViewById(R.id.txtDelTaskDesc);
        txtDelTaskFreq = findViewById(R.id.txtDelTaskFreq);
        txtDelTaskDifficulty = findViewById(R.id.txtDelTaskDifficulty);
        addDateTextView = findViewById(R.id.addDateTextView);

        txtDelTaskName.setText(name);
        txtDelTaskDesc.setText(desc);
        txtDelTaskFreq.setText(freq);
        txtDelTaskDifficulty.setText(difficulty);
        addDateTextView.setText(date);

        txtDelTaskName.setEnabled(false);
        txtDelTaskDesc.setEnabled(false);
        txtDelTaskFreq.setEnabled(false);
        txtDelTaskDifficulty.setEnabled(false);
        addDateTextView.setEnabled(false);

        deleteTaskBtn = findViewById(R.id.deleteTaskBtn);
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
        result_intent.putExtra("task_id", taskID);
        result_intent.putExtra("task_name", name);
        setResult(3, result_intent);
        finish();
    }
}