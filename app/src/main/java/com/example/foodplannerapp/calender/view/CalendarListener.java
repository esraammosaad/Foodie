package com.example.foodplannerapp.calender.view;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Meal;

public interface CalendarListener {

    void onClickListener(CalenderMealModel meal);
    void onItemClickListener(CalenderMealModel meal);

}
