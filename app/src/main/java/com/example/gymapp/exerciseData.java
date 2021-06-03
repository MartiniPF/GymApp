package com.example.gymapp;

public class exerciseData {
    public String exname;
    public String date;
    public int weight;
    public int reps;

    exerciseData(String exname, String date, int weight, int reps){
        this.exname = exname;
        this.date = date;
        this.weight = weight;
        this.reps = reps;

    }
    exerciseData(){

    }


    public String getExname() {
        return exname;
    }

    public void setExname(String exname) {
        this.exname = exname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    @Override
    public String toString() {
        return "exerciseData{" +
                "exname='" + exname + '\'' +
                ", date='" + date + '\'' +
                ", weight=" + weight +
                ", reps=" + reps +
                '}';
    }
}
