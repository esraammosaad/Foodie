package com.example.foodplannerapp.data.network.database;

import com.google.firebase.firestore.QuerySnapshot;

public interface GetDataFromRemoteDatabaseCallBack {
    void onGetFavoriteMeals(QuerySnapshot mealsList);

    void onGetCalendarMeals(QuerySnapshot mealsList);


}
