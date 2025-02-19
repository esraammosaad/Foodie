package com.example.foodplannerapp.onboarding.data.repo;

import com.example.foodplannerapp.onboarding.data.local.SharedPreferencesManager;

public class OnBoardingRepositoryImpl {
    SharedPreferencesManager sharedPreferencesManager;
    static private OnBoardingRepositoryImpl instance;
    private OnBoardingRepositoryImpl(SharedPreferencesManager sharedPreferencesManager){
        this.sharedPreferencesManager=sharedPreferencesManager;

    }

    static public OnBoardingRepositoryImpl getInstance(SharedPreferencesManager sharedPreferencesManager){
        if(instance==null){

            instance=new OnBoardingRepositoryImpl(sharedPreferencesManager);
        }

        return instance;
    }

    public void setOnBoardingState(){
        sharedPreferencesManager.saveOnBoardingState();

    }

    public boolean getOnBoardingState(){
        return sharedPreferencesManager.getOnBoardingState();
    }

}
