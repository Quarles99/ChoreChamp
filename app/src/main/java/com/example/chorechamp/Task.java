package com.example.chorechamp;

import androidx.annotation.NonNull;

public class Task {

    public String user, roomID;
    public String taskName;
    public boolean completed;
    public int dueDate;


    @Override
    public String toString(){
        String returnString;

        returnString = taskName + ", " + user + ", " + dueDate;

        return returnString;
    }

    public Task(){}
    public Task(String user, String taskName, int dueDate, String roomID){
        this.user = user;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.roomID = roomID;

        completed = false;
    }
    public boolean isCompleted() {
        return completed;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setTaskName(String tN){
        taskName = tN;
    }

    public String getUserName(){
        return user;
    }

    public void setUserName(String uN){
        user = uN;
    }

    public int getDueDate(){
        return dueDate;
    }

    public void setDueDate(int dD){
        dueDate = dD;
    }

}
