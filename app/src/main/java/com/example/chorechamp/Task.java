package com.example.chorechamp;

import androidx.annotation.NonNull;

public class Task {

    public String user;
    public String taskName;
    public int dueDate;

    @NonNull
    public String toString(){
        String returnString;

        returnString = taskName + ", " + user + ", " + dueDate;

        return returnString;
    }

    public Task(String user, String taskName, int dueDate){
        this.user = user;
        this.taskName = taskName;
        this.dueDate = dueDate;
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
