package com.example.foodplannerapp.landing.data.repo;

import com.example.foodplannerapp.utilis.SharedPreferencesManager;

public class OnBoardingRepositoryImpl {
    private final SharedPreferencesManager sharedPreferencesManager;
    private static OnBoardingRepositoryImpl instance;

    private OnBoardingRepositoryImpl(SharedPreferencesManager sharedPreferencesManager) {
        this.sharedPreferencesManager = sharedPreferencesManager;

    }

    public static OnBoardingRepositoryImpl getInstance(SharedPreferencesManager sharedPreferencesManager) {
        if (instance == null) {

            instance = new OnBoardingRepositoryImpl(sharedPreferencesManager);
        }

        return instance;
    }

    public void setOnBoardingState() {
        sharedPreferencesManager.saveOnBoardingState();

    }

    public boolean getOnBoardingState() {
        return sharedPreferencesManager.getOnBoardingState();
    }

    public boolean getThemeState() {
        return sharedPreferencesManager.getThemeState();
    }
    public void setThemeState(boolean themeState){

        sharedPreferencesManager.saveThemeState(themeState);
    }

}
