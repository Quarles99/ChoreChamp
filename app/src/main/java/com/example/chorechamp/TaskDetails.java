package com.example.chorechamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class TaskDetails extends AppCompatActivity {

    private DatabaseReference tDatabase;
    private TextView taskNameTV;
    private TextView userNameTV;
    private TextView dueDateTV;
    private Button goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        String name = intent.getStringExtra("Room");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        tDatabase = database.getReference("tasks");

        taskNameTV = findViewById(R.id.taskName);
        userNameTV = findViewById(R.id.taskUser);
        dueDateTV = findViewById(R.id.taskDueDate);

        goBack = findViewById(R.id.returnButton);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), RoomHome.class);
                back.putExtra("ID", id);
                back.putExtra("name", name);
                startActivity(back);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String taskName = intent.getStringExtra("TaskName");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        tDatabase = database.getReference("tasks");
        String ID = intent.getStringExtra("ID");

        tDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String taskN = "";
                String userN = "";
                int dueDate = 0;

                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                for (DataSnapshot d: snapshot.getChildren()) {
                    Task t = (Task) d.getValue();
                    Log.d("Task Name", t.getTaskName().toString());
                    if (t.getTaskName().equals(taskName)) {
                        taskN = t.getTaskName();
                        userN = t.getUser();
                        dueDate = t.getDueDate();
                    }
                }
                taskNameTV.setText(taskN);
                userNameTV.setText(userN);
                dueDateTV.setText(dueDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}