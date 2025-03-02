package com.example.foodplannerapp.data.repo;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseCallBack;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseServices;

public class FireStoreRepositoryImpl {

    RemoteDatabaseServices remoteDatabaseServices;
    private static FireStoreRepositoryImpl instance;


    private FireStoreRepositoryImpl(RemoteDatabaseServices remoteDatabaseServices) {
        this.remoteDatabaseServices = remoteDatabaseServices;
    }

    public static FireStoreRepositoryImpl getInstance(RemoteDatabaseServices remoteDatabaseServices) {
        if (instance == null) {

            instance = new FireStoreRepositoryImpl(remoteDatabaseServices);
        }

        return instance;

    }

    public void insertFavoriteMealToFireStore(FavoriteMealModel meal, RemoteDatabaseCallBack fireStoreCallBack) {

        remoteDatabaseServices.insertFavoriteMealToFireStore(meal, fireStoreCallBack);

    }
    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal, RemoteDatabaseCallBack fireStoreCallBack) {

        remoteDatabaseServices.deleteFavoriteMealFromFireStore(meal, fireStoreCallBack);

    }
    public void insertCalendarMealToFireStore(CalenderMealModel meal, RemoteDatabaseCallBack fireStoreCallBack) {

        remoteDatabaseServices.insertMealInTheCalendarToFireStore(meal, fireStoreCallBack);

    }
    public void deleteCalendarMealFromFireStore(CalenderMealModel meal, RemoteDatabaseCallBack fireStoreCallBack) {

        remoteDatabaseServices.deleteMealInTheCalendarFromFireStore(meal, fireStoreCallBack);

    }
}
