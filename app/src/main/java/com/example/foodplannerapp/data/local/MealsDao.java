package com.example.foodplannerapp.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplannerapp.data.local.model.MealLocalModel;

import java.util.List;

@Dao
public interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealLocalModel mealLocalModel);

    @Delete
    void deleteMeal(MealLocalModel mealLocalModel);

    @Query("select * from meal_table where userUID= :userUID")
    LiveData<List<MealLocalModel>> getAllMeals(String userUID);
}
