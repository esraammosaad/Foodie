package com.example.foodplannerapp.profile.presenter;

import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;

public class PresenterImpl {

    AuthenticationRepositoryImpl authenticationRepository;

    public PresenterImpl(AuthenticationRepositoryImpl authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void signOut(){
        authenticationRepository.signOut();

    }
}
