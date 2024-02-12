package com.example.todotoday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etAddTask;
    private Button btnSubmitTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etAddTask = findViewById(R.id.etAddTask);
        btnSubmitTask = findViewById(R.id.btnSubmitTask);
        
    }


    public void onClick(View view) {
        String task = etAddTask.getText().toString().trim();

        if (!task.isEmpty()) {
            // Start the TodoNotification service
            Intent notificationIntent = new Intent(AddTaskActivity.this, TodoNotification.class);
            notificationIntent.putExtra(TodoNotification.EXTRA_TASK_NAME, task);
            startService(notificationIntent);

            // Pass the task back to TodoActivity
            Intent intent = new Intent();
            intent.putExtra("taskName", task);
            setResult(Activity.RESULT_OK, intent);

            finish();
        } else {
            Toast.makeText(this, "Enter a task or get out", Toast.LENGTH_SHORT).show();
        }
    }

}