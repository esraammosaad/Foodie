package com.example.foodplannerapp.calender.view;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;

public interface CalendarListener {

    void onRemoveClickListener(CalenderMealModel meal);
    void onItemClickListener(CalenderMealModel meal);

}
