package com.example.foodplannerapp.data.local;

import android.content.Context;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSource {
    MealsDao mealsDao;

    public MealsLocalDataSource(Context context) {
        MyDataBase myDataBase = MyDataBase.getInstance(context);
        mealsDao = myDataBase.getDAO();


    }

    public Completable addMealToDB(FavoriteMealModel favoriteMealModel) {
        return mealsDao.insertMeal(favoriteMealModel);


    }

    public Completable deleteMealFromDB(FavoriteMealModel favoriteMealModel) {
        return mealsDao.deleteMeal(favoriteMealModel);


    }

    public Observable<List<FavoriteMealModel>> getAllMeals(String userUID) {
        return mealsDao.getAllMealsFromFavorite(userUID);
    }

    public Observable<List<FavoriteMealModel>> getMealByIDFromFavorite(String userUID, String mealID) {
        return mealsDao.getMealByIDFromFavorite(userUID, mealID);
    }

    public Observable<List<CalenderMealModel>> getMealByIDFromCalendar(String userUID, String mealID) {
        return mealsDao.getMealByIDFromCalendar(userUID, mealID);
    }

    public Completable addMealToCalendar(CalenderMealModel meal) {
        return mealsDao.insertMealToCalendar(meal);

    }

    public Completable deleteMealFromCalendar(CalenderMealModel meal) {
        return mealsDao.deleteMeal(meal);

    }

    public Observable<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID, int day, int month, int year) {
        return mealsDao.getAllMealsFromCalendar(userUID, day, month, year);
    }

}
