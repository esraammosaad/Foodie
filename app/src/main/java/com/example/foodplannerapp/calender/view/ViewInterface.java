package com.example.foodplannerapp.calender.view;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;

import java.util.List;

public interface ViewInterface {

    void onGetCalendarListFromDatabase(List<CalenderMealModel> list);

    void onSuccess(String message);

    void onFailure(String errorMessage);
}
