package com.ispc.gymapp.model;

import java.util.Set;

public class Routine {

    private String id;

    private String title;

    private String thumbnailUrl;

    private String level;

    private String muscleGroup;

    private Exercise exercise;

    public Routine() {
    }

    public Routine(String id, String title, String thumbnailUrl, String level, String muscleGroup, Exercise exercise) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.level = level;
        this.muscleGroup = muscleGroup;
        this.exercise = exercise;
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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
