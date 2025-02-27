package com.example.foodplannerapp.home.presenter;

import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.view.ViewInterface;
import com.example.foodplannerapp.utilis.SingleTransformation;
import io.reactivex.rxjava3.disposables.Disposable;


public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }


    public void getRandomMeal() {

        Disposable disposable=mealsRepository.getRandomMeal().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals().get(0)).
                subscribe(
                        meal -> viewInterface.getRandomMeal(meal),
                        error -> viewInterface.onFailure(error.getMessage()));
    }

    public void getNewRandomMeal() {

        Disposable disposable=mealsRepository.getNewRandomMeal().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals().get(0)).
                subscribe(
                        meal -> viewInterface.getRandomMeal(meal),
                        error -> viewInterface.onFailure(error.getMessage()));
    }

    public void getMealsByFirstLetter() {

        Disposable disposable=mealsRepository.getMealsByFirstLetter().
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals()).
                subscribe(
                        mealList -> viewInterface.getMealsByFirstLetter(mealList),
                        error -> viewInterface.onFailure(error.getMessage()));
    }


}
