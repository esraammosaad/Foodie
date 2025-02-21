package com.example.foodplannerapp.details.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.local.model.MealLocalModel;
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

    public void addMealToFav(MealLocalModel meal){

        mealsRepository.addMealToFavorite(meal);
    }
    public void deleteMealFromFav(MealLocalModel meal){

        mealsRepository.deleteMealFromFavorite(meal);
    }
    public LiveData<List<MealLocalModel>> getAllFavoriteMeals(String userUID){
        return mealsRepository.getAllFavoriteMeals(userUID);
    }

    public FirebaseUser getCurrentUser(){

        return authenticationRepository.getCurrentUser();
    }



}
