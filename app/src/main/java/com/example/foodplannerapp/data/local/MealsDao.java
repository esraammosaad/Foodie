package com.example.foodplannerapp.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(FavoriteMealModel favoriteMealModel);

    @Delete
    Completable deleteMeal(FavoriteMealModel favoriteMealModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMealToCalendar(CalenderMealModel calenderMealModel);

    @Delete
    Completable deleteMeal(CalenderMealModel calenderMealModel);

    @Query("select * from meal_table where userUID= :userUID")
    Flowable<List<FavoriteMealModel>> getAllMealsFromFavorite(String userUID);
    @Query("select * from meal_table where userUID= :userUID AND idMeal= :mealID")
    Flowable<List<FavoriteMealModel>> getMealByIDFromFavorite(String userUID, String mealID);
    @Query("select * from calendar_meal_table where userUID= :userUID AND idMeal= :mealID")
    Flowable<List<CalenderMealModel>> getMealByIDFromCalendar(String userUID, String mealID);

    @Query("select * from calendar_meal_table where userUID= :userUID AND day= :day AND month= :month AND year= :year")
    Flowable<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID,int day , int month , int year);
}
