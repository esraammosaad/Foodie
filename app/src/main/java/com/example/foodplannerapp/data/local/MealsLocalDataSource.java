package com.example.foodplannerapp.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.data.local.model.MealLocalModel;

import java.util.List;

public class MealsLocalDataSource {
    MealsDao mealsDao;
    LiveData<List<MealLocalModel>> mealsList;

    public MealsLocalDataSource(Context context) {
        MyDataBase myDataBase = MyDataBase.getInstance(context);
        mealsDao = myDataBase.getDAO();
        mealsList = mealsDao.getAllMeals();


    }

    public void addMealToDB(MealLocalModel mealLocalModel) {
        new Thread(

                () -> {
                    mealsDao.insertMeal(mealLocalModel);
                }
        ).start();


    }

    public void deleteMealFromDB(MealLocalModel mealLocalModel) {
        new Thread(

                () -> {
                    mealsDao.deleteMeal(mealLocalModel);
                }
        ).start();


    }

    public LiveData<List<MealLocalModel>> getAllMeals() {
        return mealsList;
    }

}
