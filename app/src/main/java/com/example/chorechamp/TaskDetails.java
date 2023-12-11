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

        // This method will be called when a new intent is received.

        // Retrieve data from the new intent:
        String taskName = intent.getStringExtra("TaskName");

        // Perform actions based on the received intent data
        if (taskName != null) {
            // Specify the path to the document using the collection name and document key
            DatabaseReference documentReference = tDatabase.child(taskName);

            // Add a ValueEventListener to retrieve data from the specified document
            documentReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Handle the data snapshot here
                    if (dataSnapshot.exists()) {
                        // The document exists, retrieve data
                        String taskN = dataSnapshot.child("user").getValue(String.class);
                        String userN = dataSnapshot.child("taskName").getValue(String.class);
                        int dueDate = Integer.parseInt(dataSnapshot.child("dueDate").getValue(String.class));
                        String roomID = dataSnapshot.child("roomID").getValue(String.class);

                        taskNameTV.setText(taskN);
                        userNameTV.setText(userN);
                        dueDateTV.setText(dueDate);

                    } else {
                        // The document does not exist
                        Log.d("FirebaseData", "Document does not exist");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}