package com.example.foodplannerapp.search_details.view;

import com.example.foodplannerapp.data.models.MealByFilter;

import java.util.List;

public interface ViewInterface {
    void onSuccess(List<MealByFilter> list);

    void onFailure(String errorMessage);
}
