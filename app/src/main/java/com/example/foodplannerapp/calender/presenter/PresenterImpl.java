package com.example.foodplannerapp.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;

    public PresenterImpl(MealsRepositoryImpl mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    public LiveData<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID, int day , int month , int year) {

        return mealsRepository.getAllCalendarMeals(userUID, day , month,year);

    }

    public void deleteMealFromCalendar(CalenderMealModel meal){

        mealsRepository.deleteMealFromCalender(meal);
    }
    public void addMealToCalendar(CalenderMealModel meal){

        mealsRepository.addMealToCalender(meal);
    }

    public FirebaseUser getCurrentUser(){

        return mealsRepository.getCurrentUser();
    }
}
