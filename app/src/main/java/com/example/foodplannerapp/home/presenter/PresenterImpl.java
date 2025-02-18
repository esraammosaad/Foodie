package com.example.foodplannerapp.home.presenter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.view.ViewInterface;

import java.util.List;

public class PresenterImpl implements NetworkCallBack {

    MealsRepositoryImpl mealsRepository;
    private static PresenterImpl instance = null;
    ViewInterface viewInterface;

    private PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.viewInterface=viewInterface;
    }

    public static PresenterImpl getInstance(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {

        if (instance == null) {

            instance = new PresenterImpl(mealsRepository,viewInterface);
        }

        return instance;

    }

    public void getRandomMeal(){

        mealsRepository.getRandomMeal(this);
    }

    public void getMealsByFirstLetter(){

        mealsRepository.getMealsByFirstLetter(this);
    }

    @Override
    public void onSuccess(Meal meal, List<Meal> mealList) {
        if (meal != null) {

            viewInterface.getRandomMeal(meal);


        }

        if (mealList != null) {

            viewInterface.getMealsByFirstLetter(mealList);


        }

    }

    @Override
    public void onFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
