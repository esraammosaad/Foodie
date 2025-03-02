package com.example.foodplannerapp.data.repo;


import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.model.AllAreasResponse;
import com.example.foodplannerapp.data.model.AllIngredientsResponse;
import com.example.foodplannerapp.data.model.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.model.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.model.Meal;
import com.example.foodplannerapp.data.model.MealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public class MealsRepositoryImpl {

    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;
    private static MealsRepositoryImpl instance;


    private MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (instance == null) {

            instance = new MealsRepositoryImpl(mealsRemoteDataSource, mealsLocalDataSource);
        }

        return instance;

    }

    public Single<MealModel> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
    }

    public Single<MealModel> getNewRandomMeal() {
        return mealsRemoteDataSource.getNewRandomMeal();
    }

    public Single<MealModel> getMealByID(int id) {


        return mealsRemoteDataSource.getMealByID(id);


    }

    public Single<MealModel> getMealsByFirstLetter() {

        return mealsRemoteDataSource.getMealsByFirstLetter();


    }

    public Single<GetAllCategoriesResponse> getAllCategories() {

        return mealsRemoteDataSource.getAllCategories();

    }

    public Single<AllAreasResponse> getAllAreas() {

        return mealsRemoteDataSource.getAllAreas();
    }

    public Single<AllIngredientsResponse> getAllIngredients() {

        return mealsRemoteDataSource.getAllIngredients();
    }

    public Single<GetMealsByFilterResponse> getAllMealsByCategory(String categoryName) {
        return mealsRemoteDataSource.getAllMealsByCategory(categoryName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByArea(String areaName) {
        return mealsRemoteDataSource.getAllMealsByArea(areaName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByIngredient(String ingredientName) {
        return mealsRemoteDataSource.getAllMealsByIngredient(ingredientName);
    }

    public Completable addMealToFavorite(FavoriteMealModel meal) {

        return mealsLocalDataSource.addMealToDB(meal);
    }

    public Completable deleteMealFromFavorite(FavoriteMealModel meal) {

        return mealsLocalDataSource.deleteMealFromDB(meal);
    }

    public Flowable<List<FavoriteMealModel>> getAllFavoriteMeals(String userUID) {
        return mealsLocalDataSource.getAllMeals(userUID);
    }

    public Completable addMealToCalender(CalenderMealModel meal) {


        return mealsLocalDataSource.addMealToCalendar(meal);


    }

    public Completable deleteMealFromCalender(CalenderMealModel meal) {


        return mealsLocalDataSource.deleteMealFromCalendar(meal);


    }

    public Flowable<List<CalenderMealModel>> getAllCalendarMeals(String userUID, int day, int month, int year) {

        return mealsLocalDataSource.getAllMealsFromCalendar(userUID, day, month, year);
    }

    public Flowable<List<FavoriteMealModel>> getMealByIDFromFavorite(String userUID, String mealID) {
        return mealsLocalDataSource.getMealByIDFromFavorite(userUID, mealID);
    }

    public Flowable<List<CalenderMealModel>> getMealByIDFromCalendar(String userUID, String mealID) {
        return mealsLocalDataSource.getMealByIDFromCalendar(userUID, mealID);
    }

    public void addMealToMobileCalendar(int year, int month, int day, Meal meal) {
        mealsLocalDataSource.addMealToMobileCalendar(year, month, day, meal);
    }

    public void deleteMealToMobileCalendar(int year, int month, int day, Meal meal) {
        mealsLocalDataSource.deleteMealFromMobileCalendar(year, month, day, meal);
    }


    public FirebaseUser getCurrentUser() {

        return AuthenticationServices.getInstance().getCurrentUser();
    }


}
