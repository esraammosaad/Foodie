package com.example.foodplannerapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

@Dao
public interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(FavoriteMealModel favoriteMealModel);

    @Delete
    void deleteMeal(FavoriteMealModel favoriteMealModel);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToCalendar(CalenderMealModel calenderMealModel);

    @Delete
    void deleteMeal(CalenderMealModel calenderMealModel);

    @Query("select * from meal_table where userUID= :userUID")
    LiveData<List<FavoriteMealModel>> getAllMeals(String userUID);

    @Query("select * from calendar_meal_table where userUID= :userUID")
    LiveData<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID);
}
