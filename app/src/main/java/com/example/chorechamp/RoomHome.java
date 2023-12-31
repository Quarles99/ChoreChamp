package com.example.chorechamp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private TextView textID;
    String roomID;
    private Button back;
    private boolean isDataSnapshotReady = false;
    private DataSnapshot tasksDataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);
        Intent intent = getIntent();
        roomID = intent.getStringExtra("ID");

        String roomName = intent.getStringExtra("name");

        back = findViewById(R.id.backButt);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        tasksRef = firebaseDatabase.getReference().child("tasks");
        retrieveTasksAndSort(roomID);
        addTask = findViewById(R.id.addTask);
        title = findViewById(R.id.textViewRoomName);
        title.setText(roomName);
        textID = findViewById(R.id.textViewIDName);
        String text = "ID: " + roomID;
        textID.setText(text);
        ListView listViewToDo = findViewById(R.id.listViewToDo);
        ListView listViewCompleted = findViewById(R.id.listViewCompleted);
        choresToDoList = new ArrayList<>();
        choresCompletedList = new ArrayList<>();
        toDoAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textViewChore, choresToDoList);
        completedAdapter = new ArrayAdapter<>(this, R.layout.list_item2, R.id.textViewChore, choresCompletedList);
        listViewToDo.setAdapter(toDoAdapter);
        listViewCompleted.setAdapter(completedAdapter);
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
            taskDetails.putExtra("ID", roomID);
            taskDetails.putExtra("name", roomName);
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
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTask.class);
                i.putExtra("ID", roomID);
                i.putExtra("name", roomName);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void retrieveTasksAndSort(String desiredRoomID) {
        Query query = tasksRef.orderByChild("roomID").equalTo(desiredRoomID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasksDataSnapshot = dataSnapshot;
                isDataSnapshotReady = true;
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