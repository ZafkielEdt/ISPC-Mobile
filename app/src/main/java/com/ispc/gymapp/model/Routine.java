package com.ispc.gymapp.model;

import java.util.ArrayList;

public class Routine {

    private String id;

    private String title;

    private String thumbnailUrl;

    private String level;

    private String muscleGroup;

    private ArrayList<Exercise> exercises;

    private String user;

    public Routine() {
    }

    public Routine(String id, String title, String thumbnailUrl, String level, String muscleGroup, ArrayList<Exercise> exercises, String user) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.level = level;
        this.muscleGroup = muscleGroup;
        this.exercises = exercises;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
