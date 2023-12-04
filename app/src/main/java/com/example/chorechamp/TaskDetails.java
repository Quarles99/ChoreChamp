package com.example.chorechamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // This method will be called when a new intent is received.

        // You can retrieve data from the new intent like this:
        String taskName = intent.getStringExtra("TaskName"); // Replace "key" with the actual key used to pass data

        // Perform actions based on the received intent data
        if (taskName != null) {
            // Do something with the extra data
        }
    }

}