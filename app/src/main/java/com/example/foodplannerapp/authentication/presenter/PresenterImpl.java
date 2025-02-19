package com.example.foodplannerapp.authentication.presenter;

import android.content.Context;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.view.ViewInterface;

public class PresenterImpl implements AuthenticationCallBack {
    AuthenticationRepositoryImpl authenticationRepository;
    ViewInterface viewInterface;

    private static PresenterImpl instance;

    private PresenterImpl(AuthenticationRepositoryImpl authenticationRepository, ViewInterface viewInterface) {
        this.authenticationRepository = authenticationRepository;
        this.viewInterface = viewInterface;
    }

    public static PresenterImpl getInstance(AuthenticationRepositoryImpl authenticationRepository, ViewInterface viewInterface){
        if(instance==null){
            instance=new PresenterImpl(authenticationRepository, viewInterface);
        }

       return instance;

    }

    public void register(String email , String password){

        authenticationRepository.register(email, password, this);

    }

    public void login(String email , String password){

        authenticationRepository.login(email, password, this);
    }

    @Override
    public void onSuccess(String message) {
        viewInterface.onSuccess(message);

    }

    @Override
    public void onFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
