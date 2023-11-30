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

}
