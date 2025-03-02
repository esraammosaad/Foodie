package com.example.foodplannerapp.landing.data.repo;

import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.LocalStorageDataSource;
import com.google.firebase.auth.FirebaseUser;

public class OnBoardingRepositoryImpl {
    private final LocalStorageDataSource localStorageDataSource;
    private static OnBoardingRepositoryImpl instance;
    private AuthenticationServices authenticationServices;


    private OnBoardingRepositoryImpl(LocalStorageDataSource localStorageDataSource, AuthenticationServices authenticationServices) {
        this.localStorageDataSource = localStorageDataSource;
        this.authenticationServices=authenticationServices;

    }

    public static OnBoardingRepositoryImpl getInstance(LocalStorageDataSource localStorageDataSource, AuthenticationServices authenticationServices) {
        if (instance == null) {

            instance = new OnBoardingRepositoryImpl(localStorageDataSource, authenticationServices);
        }

        return instance;
    }

    public void setOnBoardingState() {
        localStorageDataSource.saveOnBoardingState();

    }

    public boolean getOnBoardingState() {
        return localStorageDataSource.getOnBoardingState();
    }

    public boolean getThemeState() {
        return localStorageDataSource.getThemeState();
    }
    public void setThemeState(boolean themeState){

        localStorageDataSource.saveThemeState(themeState);
    }

    public FirebaseUser getCurrentUser(){

        return authenticationServices.getCurrentUser();


    }
    public void signOut(){

         authenticationServices.signOut();


    }

}
