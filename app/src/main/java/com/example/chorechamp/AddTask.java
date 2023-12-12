package com.example.chorechamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        taskET = findViewById(R.id.taskNameET);
        userNameET = findViewById(R.id.assignedPersonET);
        dueDateET = findViewById(R.id.dueDateET);

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

                Intent intent = getIntent();
                String id = intent.getStringExtra("ID");
                String name = intent.getStringExtra("Room");

                Task t = new Task(userName, taskName, dueDate, roomID);
                Log.d("task Create", t.toString());

                String key = tData.push().getKey();
                tData.child(key).setValue(t);
                //Log.d("task Create", t.toString());

                Toast.makeText(getApplicationContext(), "Task added!", Toast.LENGTH_SHORT);
                Intent back = new Intent(getApplicationContext(), RoomHome.class);
                back.putExtra("ID", id);
                back.putExtra("name", name);
                startActivity(back);

            }
        });

    }




}