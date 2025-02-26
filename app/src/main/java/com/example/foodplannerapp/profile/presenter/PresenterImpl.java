package com.example.foodplannerapp.profile.presenter;

import android.content.Context;

import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.google.firebase.auth.FirebaseUser;

public class PresenterImpl {

    AuthenticationRepositoryImpl authenticationRepository;

    public PresenterImpl(AuthenticationRepositoryImpl authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public FirebaseUser getCurrentUser(){

        return authenticationRepository.getCurrentUser();
    }

    public void signOut(){
        authenticationRepository.signOut();

    }

    public void saveThemeState(Context context, boolean state){
        authenticationRepository.saveThemeState(context, state);
    }

    public boolean getThemeState(Context context){

        return authenticationRepository.getThemeState(context);

    }
}
