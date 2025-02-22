package com.example.foodplannerapp.search_details.presenter;

import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.models.MealByFilter;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search_details.view.ViewInterface;

import java.util.List;

public class PresenterImpl implements NetworkCallBack<MealByFilter> {

    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    public void getMealsByCategory(String categoryName){

        mealsRepository.getAllMealsByCategory(this,  categoryName);

    }
    public void getMealsByArea(String areaName){

        mealsRepository.getAllMealsByArea(this,  areaName);

    }
    public void getMealsByIngredient(String ingredientName){

        mealsRepository.getAllMealsByIngredient(this,  ingredientName);

    }

    @Override
    public void onSuccess(Meal meal, List<MealByFilter> list) {
        viewInterface.onSuccess(list);

    }

    @Override
    public void onFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
