package com.example.foodplannerapp.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.network.database.FireStoreCallBack;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.view.ViewInterface;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PresenterImpl implements FireStoreCallBack {

    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository,FireStoreRepositoryImpl fireStoreRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.fireStoreRepository=fireStoreRepository;
        this.viewInterface=viewInterface;
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
    public void deleteCalendarMealFromFireStore(CalenderMealModel meal){

        fireStoreRepository.deleteCalendarMealFromFireStore(meal,this);
    }

    public void insertCalendarMealToFireStore(CalenderMealModel meal) {

        fireStoreRepository.insertCalendarMealToFireStore(meal, this);

    }

    @Override
    public void onFireStoreSuccess(String message) {
        viewInterface.onSuccess(message);


    }

    @Override
    public void onFireStoreFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
