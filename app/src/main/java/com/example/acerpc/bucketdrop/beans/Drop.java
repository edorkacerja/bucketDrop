package com.example.acerpc.bucketdrop.beans;

import io.realm.RealmObject;

/**
 * Created by AcerPC on 11/9/2016.
 */

public class Drop extends RealmObject {
    private String dropName;
    private long timeCreated;
    private long goalTime;
    private boolean completed;

    public Drop() {
    }

    public Drop(boolean completed, String goal, long goalTime, long timeCreated) {
        this.completed = completed;
        this.dropName = goal;
        this.goalTime = goalTime;
        this.timeCreated = timeCreated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getGoal() {
        return dropName;
    }

    public void setGoal(String goal) {
        this.dropName = goal;
    }

    public long getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(long goalTime) {
        this.goalTime = goalTime;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
