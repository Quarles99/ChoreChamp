package com.example.chorechamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTask extends AppCompatActivity {

    Button submitButton;
    String taskName;
    String userName;
    int dueDate;
    EditText taskET;
    EditText userNameET;
    EditText dueDateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        submitButton = findViewById(R.id.submitTask);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskName = taskET.getText().toString();
                userName = userNameET.getText().toString();
                dueDate = Integer.parseInt(dueDateET.getText().toString());
                Task task = new Task(taskName, userName, dueDate);
                Log.d("task Create", task.toString());
                // Add task to firebase here
            }
        });

    }



}