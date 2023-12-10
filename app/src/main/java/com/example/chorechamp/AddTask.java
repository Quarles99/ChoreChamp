package com.example.chorechamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTask extends AppCompatActivity {

    Button submitButton;
    String taskName;
    String userName;
    int dueDate;
    EditText taskET;
    EditText userNameET;
    EditText dueDateET;
    DatabaseReference rData;
    DatabaseReference tData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        rData = database.getReference("rooms");
        tData = database.getReference("tasks");

        submitButton = findViewById(R.id.submitTask);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskName = taskET.getText().toString();
                userName = userNameET.getText().toString();
                dueDate = Integer.parseInt(dueDateET.getText().toString().replace("/",""));
                
                //Get Room ID
                Intent i = getIntent();
                String roomID = i.getStringExtra("ID");

                Task t = new Task(userName, taskName, dueDate, roomID);

                String key = tData.push().getKey();
                tData.child(key).setValue(t);
                //Task task = new Task(taskName, userName, dueDate, roomID);
                //Log.d("task Create", task.toString());
                // Add task to firebase here
            }
        });

    }



}