package com.example.foodplannerapp.details.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.view.ViewInterface;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PresenterImpl implements NetworkCallBack {

    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;


    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.viewInterface=viewInterface;
    }


    public void getMealByID(int id){

        mealsRepository.getMealByID(this,id);
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



    public void addMealToCalendar(CalenderMealModel meal){

        mealsRepository.addMealToCalender(meal);
    }
    public void deleteMealFromCalendar(CalenderMealModel meal){

        mealsRepository.deleteMealFromCalender(meal);
    }
    public LiveData<List<CalenderMealModel>> getAllCalendarMeals(String userUID, int day , int month , int year){

        return mealsRepository.getAllCalendarMeals(userUID, day, month, year);
    }

    public FirebaseUser getCurrentUser(){

        return mealsRepository.getCurrentUser();
    }


    @Override
    public void onSuccess(Meal meal, List list) {

        viewInterface.onSuccess(meal);

    }

    @Override
    public void onFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
