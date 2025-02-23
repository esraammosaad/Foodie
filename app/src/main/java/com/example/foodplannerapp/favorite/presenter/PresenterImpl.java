package com.example.foodplannerapp.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.database.FireStoreCallBack;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.favorite.view.ViewInterface;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class PresenterImpl implements FireStoreCallBack {
    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;


    public PresenterImpl(MealsRepositoryImpl mealsRepository, FireStoreRepositoryImpl fireStoreRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.fireStoreRepository=fireStoreRepository;
        this.viewInterface=viewInterface;
    }



    public LiveData<List<FavoriteMealModel>> getAllFavoriteMeals(String userUID){
        return mealsRepository.getAllFavoriteMeals(userUID);
    }

    public void deleteMealFromFavorite(FavoriteMealModel meal){

        mealsRepository.deleteMealFromFavorite(meal);

    }
    public void addMealToFavorite(FavoriteMealModel meal){

        mealsRepository.addMealToFavorite(meal);

    }

    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal){

        fireStoreRepository.deleteFavoriteMealFromFireStore(meal,this);
    }

    public void insertCalendarMealToFireStore(FavoriteMealModel meal) {

        fireStoreRepository.insertFavoriteMealToFireStore(meal, this);

    }

    public FirebaseUser getCurrentUser(){

        return mealsRepository.getCurrentUser();
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
