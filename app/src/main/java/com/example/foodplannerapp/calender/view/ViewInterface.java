package com.example.foodplannerapp.calender.view;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;

import java.util.List;

public interface ViewInterface {
    void onSuccess(String message);
    void onCalendarListDatabaseSuccess(List<CalenderMealModel> list);
    void onFailure(String errorMessage);
}
