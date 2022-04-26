package com.google.todoappfirebase;

import android.os.Parcel;
import android.os.Parcelable;

public class Task {
    private String Title, Start, End, Description, Key;

    public Task() {
    }

    public Task(String title, String start, String end, String description) {
        Title = title;
        Start = start;
        End = end;
        Description = description;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setStart(String start) {
        Start = start;
    }

    public void setEnd(String end) {
        End = end;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getTitle() {
        return Title;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getDescription() {
        return Description;
    }

    public String getKey() {
        return Key;
    }
}
