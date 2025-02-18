package com.example.foodplannerapp.data.repo;

import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.NetworkCallBack;


public class MealsRepositoryImpl {

    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;

   private static MealsRepositoryImpl instance;

    private MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }
    public static MealsRepositoryImpl getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource){
        if(instance==null){

            instance = new MealsRepositoryImpl(mealsRemoteDataSource, mealsLocalDataSource);
        }

        return instance;

    }

    public void getRandomMeal(NetworkCallBack networkCallBack){


        mealsRemoteDataSource.getRandomMeal(networkCallBack);



    }

    public void getMealsByFirstLetter(NetworkCallBack networkCallBack){

        mealsRemoteDataSource.getMealsByFirstLetter(networkCallBack);


    }


}
