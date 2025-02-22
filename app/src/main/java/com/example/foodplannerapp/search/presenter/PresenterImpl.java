package com.example.foodplannerapp.search.presenter;

import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search.view.ViewInterface;

import java.util.List;

public class PresenterImpl implements NetworkCallBack {
    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    public void getAllCategories() {

        mealsRepository.getAllCategories(this);
    }

    public void getAllAreas(){

        mealsRepository.getAllAreas(this);
    }

    public void getAllIngredients(){

        mealsRepository.getAllIngredients(this);
    }


    @Override
    public void onSuccess(Meal meal, List list) {

        viewInterface.onSuccess(list);

    }

    @Override
    public void onFailure(String errorMessage) {

        viewInterface.onFailure(errorMessage);

    }
}
