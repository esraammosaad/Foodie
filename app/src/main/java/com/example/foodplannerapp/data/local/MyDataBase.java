package com.example.foodplannerapp.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.foodplannerapp.data.local.model.MealLocalModel;


@Database(entities = {MealLocalModel.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase instance = null;

    public abstract MealsDao getDAO();

    public static MyDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDataBase.class, "mealDB").build();
        }


        return instance;
    }
}
