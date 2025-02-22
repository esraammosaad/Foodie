package com.example.foodplannerapp.search.view;

import com.example.foodplannerapp.data.models.Meal;

public interface SearchListener<T> {
    void onClickListener(T item);
}
