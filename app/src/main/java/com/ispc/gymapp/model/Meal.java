package com.ispc.gymapp.model;

import java.util.List;

public class Meal {
    private String id;
    private String name;
    private String description;
    private int calories;
    private float proteins;
    private float carbs;
    private float fats;
    private List<MealIngredient> ingredients;
    private MealType type;
    private String imageUrl;
}
