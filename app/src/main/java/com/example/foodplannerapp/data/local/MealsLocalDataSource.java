package com.example.foodplannerapp.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

public class MealsLocalDataSource {
    MealsDao mealsDao;
    LiveData<List<FavoriteMealModel>> mealsList;

    public MealsLocalDataSource(Context context) {
        MyDataBase myDataBase = MyDataBase.getInstance(context);
        mealsDao = myDataBase.getDAO();



    }

    public void addMealToDB(FavoriteMealModel favoriteMealModel) {
        new Thread(

                () -> {
                    mealsDao.insertMeal(favoriteMealModel);
                }
        ).start();


    }

    public void deleteMealFromDB(FavoriteMealModel favoriteMealModel) {
        new Thread(

                () -> {
                    mealsDao.deleteMeal(favoriteMealModel);
                }
        ).start();


    }

    public LiveData<List<FavoriteMealModel>> getAllMeals(String userUID) {
        mealsList = mealsDao.getAllMeals(userUID);
        return mealsList;
    }

}
