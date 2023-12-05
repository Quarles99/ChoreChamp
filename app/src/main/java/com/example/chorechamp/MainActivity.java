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

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

EditText username, roomid, roomName;
Button joinButton, createButton;
private DatabaseReference rData, tData;
private boolean roomExist;


    View.OnClickListener join = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //get the room id
            String ID = roomid.getText().toString();
            if(ID.length() < 5){
                Toast.makeText(getApplicationContext(), "This room ID must be 5 characters",
                        Toast.LENGTH_SHORT).show();
            }else {
                //test if roomid exists, if not diplay error
                if (roomExist == false) {
                    Toast.makeText(getApplicationContext(), "This room ID is not valid",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), RoomHome.class);
                    i.putExtra("ID", ID);
                    startActivity(i);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.userET);
        roomid = findViewById(R.id.joinRoom);
        roomName = findViewById(R.id.roomName);

        joinButton = findViewById(R.id.joinButton);
        createButton = findViewById(R.id.createRoom);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rData = database.getReference("rooms");
        tData = database.getReference("tasks");

        roomExist = false;

    }

    public void doesRoomExist() {
        rData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String ID = roomid.getText().toString();
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                for (DataSnapshot d : snapshot.getChildren()) {
                    Room r = (Room) d.getValue();
                    if (r.getId() == ID) {
                        roomExist = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}