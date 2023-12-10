package com.example.chorechamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RoomHome extends AppCompatActivity {

    private ArrayList<String> choresToDoList;
    private ArrayList<String> choresCompletedList;

    private ArrayAdapter<String> toDoAdapter;
    private ArrayAdapter<String> completedAdapter;
    private Button addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_home);

        addTask = findViewById(R.id.addTask);

        Intent intent = getIntent();
        String randID = intent.getStringExtra("ID");


        ListView listViewToDo = findViewById(R.id.listViewToDo);
        ListView listViewCompleted = findViewById(R.id.listViewCompleted);

        choresToDoList = new ArrayList<>();
        choresCompletedList = new ArrayList<>();

        // Add some example chores to do
        choresToDoList.add("Clean the room");
        choresToDoList.add("Do laundry");
        choresToDoList.add("Buy groceries");

        // Create adapters for the lists
        toDoAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textViewChore, choresToDoList);
        completedAdapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.textViewChore, choresCompletedList);


        // Set adapters to the list views
        listViewToDo.setAdapter(toDoAdapter);
        listViewCompleted.setAdapter(completedAdapter);

        // Simulate moving items from "To Do" list to "Completed" list (for example, on item click)
        listViewToDo.setOnItemClickListener((parent, view, position, id) -> {
            String completedChore = choresToDoList.get(position);
            choresCompletedList.add(completedChore);
            choresToDoList.remove(position);
            toDoAdapter.notifyDataSetChanged();
            completedAdapter.notifyDataSetChanged();
        });

        listViewCompleted.setOnItemClickListener((parent, view, position, id) -> {
            String notCompletedChore = choresCompletedList.get(position);
            choresToDoList.add(notCompletedChore);
            choresCompletedList.remove(position);
            completedAdapter.notifyDataSetChanged();
            toDoAdapter.notifyDataSetChanged();
        });

        //Add task listener
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTask.class);
                i.putExtra("ID", randID);
                startActivity(i);
            }
        });
    }
}
