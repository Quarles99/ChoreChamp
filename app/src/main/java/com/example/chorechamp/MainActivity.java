package com.example.chorechamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

EditText roomid, roomName;
Button joinButton, createButton;
private DatabaseReference rData, tData;
private boolean roomExist, roomExists;
private String name;


    View.OnClickListener join = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String ID = roomid.getText().toString();

            doesRoomExist(ID);
        }
    };

    View.OnClickListener createRoom = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String newName = roomName.getText().toString();
            Random gen = new Random();
            String randID;

            do {
                randID = Integer.toString(gen.nextInt(99999));
            } while (doesRoomExistSynchronously(randID));

            Room r = new Room(newName, randID);
            String key = rData.push().getKey();
            rData.child(key).setValue(r);

            Intent i = new Intent(getApplicationContext(), RoomHome.class);
            i.putExtra("ID", randID);
            i.putExtra("name", newName);
            startActivity(i);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roomid = findViewById(R.id.joinRoom);
        roomName = findViewById(R.id.roomName);

        joinButton = findViewById(R.id.joinButton);
        createButton = findViewById(R.id.createRoom);
        joinButton.setOnClickListener(join);
        createButton.setOnClickListener(createRoom);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rData = database.getReference("rooms");
        tData = database.getReference("tasks");


    }


    public void doesRoomExist(String abc) {
        rData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ID = abc;
                roomExist = false;

                for (DataSnapshot d : snapshot.getChildren()) {
                    HashMap<String, Object> roomData = (HashMap<String, Object>) d.getValue();
                    Room r = new Room(roomData.get("name").toString(), roomData.get("id").toString());
                    if (r.getId().equals(ID)) {
                        roomExist = true;
                        name = r.getName();
                        break;
                    }
                }
                handleRoomExistenceCheck();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation if needed
            }
        });
    }
    private void handleRoomExistenceCheck() {
        String ID = roomid.getText().toString();
        if (ID.length() < 5) {
            Toast.makeText(getApplicationContext(), "This room ID must be 5 characters", Toast.LENGTH_SHORT).show();
        } else {
            if (roomExist) {
                Intent i = new Intent(getApplicationContext(), RoomHome.class);
                i.putExtra("ID", ID);
                i.putExtra("name", name);

                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "This room ID is not valid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean doesRoomExistSynchronously(String ID) {
        roomExists = false;
        rData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    HashMap<String, Object> roomData = (HashMap<String, Object>) d.getValue();
                    Room r = new Room(roomData.get("name").toString(), roomData.get("id").toString());
                    if (r.getId().equals(ID)) {
                        roomExists = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancellation if needed
            }
        });
        return roomExists;
    }
}