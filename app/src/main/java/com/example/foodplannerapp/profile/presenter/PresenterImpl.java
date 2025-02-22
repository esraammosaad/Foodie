package com.example.foodplannerapp.profile.presenter;

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
}
