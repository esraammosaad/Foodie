package com.example.foodplannerapp.home.presenter;

import android.annotation.SuppressLint;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.view.ViewInterface;
import com.example.foodplannerapp.utilis.SingleTransformation;


public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }


    @SuppressLint("CheckResult")
    public void getRandomMeal() {

        mealsRepository.getRandomMeal().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals().get(0)).
                subscribe(
                        meal -> viewInterface.getRandomMeal(meal),
                        error -> viewInterface.onFailure(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getNewRandomMeal() {

        mealsRepository.getNewRandomMeal().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals().get(0)).
                subscribe(
                        meal -> viewInterface.getRandomMeal(meal),
                        error -> viewInterface.onFailure(error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getMealsByFirstLetter() {

        mealsRepository.getMealsByFirstLetter().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals()).
                subscribe(
                        mealList -> viewInterface.getMealsByFirstLetter(mealList),
                        error -> viewInterface.onFailure(error.getMessage()));
    }


}
