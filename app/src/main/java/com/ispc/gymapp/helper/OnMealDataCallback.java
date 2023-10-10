package com.ispc.gymapp.helper;

import com.ispc.gymapp.model.Meal;

import java.util.List;

public interface OnMealDataCallback {
    void onMealsFetched(List<Meal> meals);
    void onError(Exception e);
}
