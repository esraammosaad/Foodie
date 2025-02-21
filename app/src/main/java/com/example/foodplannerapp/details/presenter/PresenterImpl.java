package com.example.foodplannerapp.details.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;
    AuthenticationRepositoryImpl authenticationRepository;


    public PresenterImpl(MealsRepositoryImpl mealsRepository, AuthenticationRepositoryImpl authenticationRepository) {

        this.mealsRepository = mealsRepository;
        this.authenticationRepository=authenticationRepository;
    }



   public ArrayList<Ingredient> getIngredients(Meal meal){
        return  mealsRepository.getIngredientsList(meal);

    }

    public void addMealToFav(FavoriteMealModel meal){

        mealsRepository.addMealToFavorite(meal);
    }
    public void deleteMealFromFav(FavoriteMealModel meal){

        mealsRepository.deleteMealFromFavorite(meal);
    }
    public LiveData<List<FavoriteMealModel>> getAllFavoriteMeals(String userUID){
        return mealsRepository.getAllFavoriteMeals(userUID);
    }

    public FirebaseUser getCurrentUser(){

        return authenticationRepository.getCurrentUser();
    }

    public void addMealToCalendar(CalenderMealModel meal){

        mealsRepository.addMealToCalender(meal);
    }
    public void deleteMealFromCalendar(CalenderMealModel meal){

        mealsRepository.deleteMealFromCalender(meal);
    }
    public LiveData<List<CalenderMealModel>> getAllCalendarMeals(String userUID, int day , int month , int year){

        return mealsRepository.getAllCalendarMeals(userUID, day, month, year);
    }



}
