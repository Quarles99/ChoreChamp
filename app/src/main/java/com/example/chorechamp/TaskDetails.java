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

        // Retrieve data from the new intent:
        String taskName = intent.getStringExtra("TaskName");

        // Perform actions based on the received intent data
        if (taskName != null) {
            // Do something with the extra data
        }
    }

}