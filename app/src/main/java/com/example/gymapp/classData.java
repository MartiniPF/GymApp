package com.example.gymapp;

import androidx.annotation.NonNull;

public class classData {
    public String name;
    public String date;
    public String time;
    public String length;
    public int capacity;
    public int attendees;

    classData(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return "classData{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", length='" + length + '\'' +
                ", capacity=" + capacity +
                ", attendees=" + attendees +
                '}';
    }
}
