package com.example.foodplannerapp.data.local;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Meal;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSource {
    MealsDao mealsDao;
    Context context;

    public MealsLocalDataSource(Context context) {
        MyDataBase myDataBase = MyDataBase.getInstance(context);
        mealsDao = myDataBase.getDAO();
        this.context = context;


    }

    public Completable addMealToDB(FavoriteMealModel favoriteMealModel) {
        return mealsDao.insertMeal(favoriteMealModel);


    }

    public Completable deleteMealFromDB(FavoriteMealModel favoriteMealModel) {
        return mealsDao.deleteMeal(favoriteMealModel);


    }

    public Flowable<List<FavoriteMealModel>> getAllMeals(String userUID) {
        return mealsDao.getAllMealsFromFavorite(userUID);
    }

    public Flowable<List<FavoriteMealModel>> getMealByIDFromFavorite(String userUID, String mealID) {
        return mealsDao.getMealByIDFromFavorite(userUID, mealID);
    }

    public Flowable<List<CalenderMealModel>> getMealByIDFromCalendar(String userUID, String mealID) {
        return mealsDao.getMealByIDFromCalendar(userUID, mealID);
    }

    public Completable addMealToCalendar(CalenderMealModel meal) {
        return mealsDao.insertMealToCalendar(meal);

    }

    public Completable deleteMealFromCalendar(CalenderMealModel meal) {
        return mealsDao.deleteMeal(meal);

    }

    public Flowable<List<CalenderMealModel>> getAllMealsFromCalendar(String userUID, int day, int month, int year) {
        return mealsDao.getAllMealsFromCalendar(userUID, day, month, year);
    }

    public void addMealToMobileCalendar(int year, int month, int day, Meal meal) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = context.
                    getContentResolver().
                    query(
                            android.provider.CalendarContract.Calendars.CONTENT_URI, new String[]{
                                    CalendarContract.Calendars._ID, CalendarContract.Calendars.NAME}, null, null, null
                    );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String text = "";

                    for (int i = 0; i < cursor.getCount(); i++) {

                        text += "ID: " + cursor.getInt(0) + "\n" + "Name: " + cursor.getString(1) + "\n\n";
                        cursor.moveToNext();
                    }

                    Log.i("TAG", "onViewCreated: " + text);

                }

            }

            ContentValues values = getContentValues(year, month, day, meal);
            Uri uri = context.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
            Log.i("TAG", "onViewCreated: " + uri.toString());

        }
    }

    public void deleteMealFromMobileCalendar(int year, int month, int day, Meal meal) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            String selection = CalendarContract.Events.CALENDAR_ID + " = ? AND " + CalendarContract.Events.TITLE + " = ? ";
            String[] selectionArgs = new String[]{String.valueOf(4), meal.getStrMeal()};
            int result = context.getContentResolver().delete(CalendarContract.Events.CONTENT_URI, selection, selectionArgs);
            Log.i("TAG", result > 0 ? "deleteMealFromMobileCalendar: success" : "deleteMealFromMobileCalendar: failed");
        }
    }

    public ContentValues getContentValues(int year, int month, int day, Meal meal) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(year, month, day, 0, 0);
        long startMillis = startCalendar.getTimeInMillis();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year, month, day, 23, 59);
        long endMillis = endCalendar.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 4);
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Egypt/Cairo");
        values.put(CalendarContract.Events.TITLE, meal.getStrMeal());
        values.put(CalendarContract.Events.DESCRIPTION, meal.getStrInstructions());
        return values;
    }

}
