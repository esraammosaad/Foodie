package com.example.foodplannerapp.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;

import java.util.List;

public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;

    public PresenterImpl(MealsRepositoryImpl mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    public LiveData<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID, int day , int month , int year) {

        return mealsRepository.getAllCalendarMeals(userUID, day , month,year);

    }
}
