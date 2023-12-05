package com.example.chorechamp;

public class User {
    String username, room;

    public User(String username, String room) {
        this.username = username;
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
