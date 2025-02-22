package com.example.foodplannerapp.search.view;

import com.example.foodplannerapp.data.models.Meal;

import java.util.List;

public interface ViewInterface<T> {
    void onSuccess(List<T> list);
    void onFailure(String errorMessage);

}
