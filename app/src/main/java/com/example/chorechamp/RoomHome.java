package com.example.chorechamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomHome extends AppCompatActivity {

    private ArrayList<String> choresToDoList;
    private ArrayList<String> choresCompletedList;

    private ArrayAdapter<String> toDoAdapter;
    private ArrayAdapter<String> completedAdapter;
    private Button addTask;
    private DatabaseReference tasksRef;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);

        Intent intent = getIntent();
        String roomID = intent.getStringExtra("ID");
        String roomName = intent.getStringExtra("name");


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        tasksRef = firebaseDatabase.getReference().child("tasks"); // Replace "tasks" with your actual tasks node

        retrieveTasksAndSort(roomID);
        addTask = findViewById(R.id.addTask);
        title = findViewById(R.id.textViewRoomName);

        title.setText(roomName);

        ListView listViewToDo = findViewById(R.id.listViewToDo);
        ListView listViewCompleted = findViewById(R.id.listViewCompleted);

        choresToDoList = new ArrayList<>();
        choresCompletedList = new ArrayList<>();

        // Add some example chores to do


        // Create adapters for the lists
        toDoAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textViewChore, choresToDoList);
        completedAdapter = new ArrayAdapter<>(this, R.layout.list_item2, R.id.textViewChore, choresCompletedList);


        // Set adapters to the list views
        listViewToDo.setAdapter(toDoAdapter);
        listViewCompleted.setAdapter(completedAdapter);

        // Simulate moving items from "To Do" list to "Completed" list (for example, on item click)
        listViewToDo.setOnItemLongClickListener((parent, view, position, id) -> {
            String completedChore = choresToDoList.get(position);
            choresCompletedList.add(completedChore);
            choresToDoList.remove(position);
            toDoAdapter.notifyDataSetChanged();
            completedAdapter.notifyDataSetChanged();
            return true;
        });
        listViewToDo.setOnItemClickListener((parent, view, position, id) -> {
            String chore = choresToDoList.get(position);
            Intent taskDetails = new Intent(getApplicationContext(), TaskDetails.class);
            taskDetails.putExtra("TaskName", chore);

            startActivity(taskDetails);
        });

        listViewCompleted.setOnItemLongClickListener((parent, view, position, id) -> {
            String notCompletedChore = choresCompletedList.get(position);
            choresToDoList.add(notCompletedChore);
            choresCompletedList.remove(position);
            completedAdapter.notifyDataSetChanged();
            toDoAdapter.notifyDataSetChanged();
            return true;
        });

        //Add task listener
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTask.class);
                i.putExtra("ID", roomID);
                startActivity(i);
            }
        });
    }
    /*private void retrieveTaskNamesForRoomID(String desiredRoomID) {
        Query query = tasksRef.orderByChild("roomID").equalTo(desiredRoomID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Task task = snapshot.getValue(Task.class);
                        if (task != null) {
                            String taskName = task.getTaskName();
                            // Do something with the taskName (e.g., display in UI, log)
                            choresToDoList.add(taskName);
                            toDoAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.d("No tasks", "No tasks found for the specified roomID");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Error fetching tasks: " + databaseError.getMessage());
            }
        });}*/
    private void retrieveTasksAndSort(String desiredRoomID) {
        Query query = tasksRef.orderByChild("roomID").equalTo(desiredRoomID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Task task = snapshot.getValue(Task.class);
                        if (task != null) {
                            String taskName = task.getTaskName();
                            if (task.isCompleted()) {
                                choresCompletedList.add(taskName);
                            } else {
                                choresToDoList.add(taskName);
                            }
                        }
                    }
                    // Update your ListView adapters here
                    toDoAdapter.notifyDataSetChanged();
                    completedAdapter.notifyDataSetChanged();
                } else {
                    Log.d("No tasks", "No tasks found for the specified roomID");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase Error", "Error fetching tasks: " + databaseError.getMessage());
            }
        });
    }

}
