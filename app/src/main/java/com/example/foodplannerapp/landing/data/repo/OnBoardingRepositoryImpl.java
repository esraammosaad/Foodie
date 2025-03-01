package com.example.foodplannerapp.landing.data.repo;

import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.utilis.SharedPreferencesManager;
import com.google.firebase.auth.FirebaseUser;

public class OnBoardingRepositoryImpl {
    private final SharedPreferencesManager sharedPreferencesManager;
    private static OnBoardingRepositoryImpl instance;
    private AuthenticationServices authenticationServices;


    private OnBoardingRepositoryImpl(SharedPreferencesManager sharedPreferencesManager, AuthenticationServices authenticationServices) {
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.authenticationServices=authenticationServices;

    }

    public static OnBoardingRepositoryImpl getInstance(SharedPreferencesManager sharedPreferencesManager, AuthenticationServices authenticationServices) {
        if (instance == null) {

            instance = new OnBoardingRepositoryImpl(sharedPreferencesManager, authenticationServices);
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

    public FirebaseUser getCurrentUser(){

        return authenticationServices.getCurrentUser();


    }
    public void signOut(){

         authenticationServices.signOut();


    }

}
