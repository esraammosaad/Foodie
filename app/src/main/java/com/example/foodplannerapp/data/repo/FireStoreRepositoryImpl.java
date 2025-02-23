package com.example.foodplannerapp.data.repo;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.database.FireStoreCallBack;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;

public class FireStoreRepositoryImpl {

    FiresStoreServices firesStoreServices;


    private static FireStoreRepositoryImpl instance;


    private FireStoreRepositoryImpl(FiresStoreServices firesStoreServices) {
        this.firesStoreServices = firesStoreServices;
    }

    public static FireStoreRepositoryImpl getInstance(FiresStoreServices firesStoreServices) {
        if (instance == null) {

            instance = new FireStoreRepositoryImpl(firesStoreServices);
        }

        return instance;

    }

    public void insertFavoriteMealToFireStore(FavoriteMealModel meal, FireStoreCallBack fireStoreCallBack) {

        firesStoreServices.insertFavoriteMealToFireStore(meal, fireStoreCallBack);

    }
    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal, FireStoreCallBack fireStoreCallBack) {

        firesStoreServices.deleteFavoriteMealFromFireStore(meal, fireStoreCallBack);

    }
    public void insertCalendarMealToFireStore(CalenderMealModel meal, FireStoreCallBack fireStoreCallBack) {

        firesStoreServices.insertMealInTheCalendarToFireStore(meal, fireStoreCallBack);

    }
    public void deleteCalendarMealFromFireStore(CalenderMealModel meal, FireStoreCallBack fireStoreCallBack) {

        firesStoreServices.deleteMealInTheCalendarFromFireStore(meal, fireStoreCallBack);

    }
}
