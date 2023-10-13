package com.ispc.gymapp.model;

public class MealIngredient {

    private String name;
    private int grams;

    public MealIngredient() {
    }

    public MealIngredient(String name, int grams) {
        this.name = name;
        this.grams = grams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }
}
