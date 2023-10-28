package com.ispc.gymapp.model;

public class Exercise {

    private String title;

    private String description;

    private int duration;

    private int caloriesBurned;

    private int sets;

    private int reps;

    private String type;

    private String thumbnailUrl;

    private String videoUrl;

    public Exercise() {
    }

    //favoritos
    private String id;



    public Exercise(String title, String description, int duration, int caloriesBurned, int sets, int reps, String thumbnailUrl, String videoUrl, String type, String id) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.sets = sets;
        this.reps = reps;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
        this.type = type;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    private boolean isFavorite;
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
