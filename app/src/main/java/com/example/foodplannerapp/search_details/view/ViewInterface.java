package com.example.foodplannerapp.search_details.view;

import com.example.foodplannerapp.data.model.MealByFilter;
import java.util.List;

public interface ViewInterface {
    void onSearch(List<MealByFilter> list);

    void onSuccess(List<MealByFilter> list);

    void onFailure(String errorMessage);
}
