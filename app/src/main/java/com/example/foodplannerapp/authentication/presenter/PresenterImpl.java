package com.example.foodplannerapp.authentication.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.view.ViewInterface;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;

public class PresenterImpl implements AuthenticationCallBack {
    AuthenticationRepositoryImpl authenticationRepository;
    ViewInterface viewInterface;

//    private static PresenterImpl instance;

    public PresenterImpl(AuthenticationRepositoryImpl authenticationRepository, ViewInterface viewInterface) {
        this.authenticationRepository = authenticationRepository;
        this.viewInterface = viewInterface;
    }

//    public static PresenterImpl getInstance(AuthenticationRepositoryImpl authenticationRepository, ViewInterface viewInterface){
//        if(instance==null){
//            instance=new PresenterImpl(authenticationRepository, viewInterface);
//        }
//
//       return instance;
//
//    }

    public void register(String email , String password){

        authenticationRepository.register(email, password, this);

    }

    public void login(String email , String password){

        authenticationRepository.login(email, password, this);
    }

    public void loginWithGoogle(ActivityResult result) throws ApiException {

        authenticationRepository.loginWithGoogle(result ,this);


    }
    public GoogleSignInClient initGoogleSignIn(Context context){

         return  authenticationRepository.initGoogleSignIn(context);
    }

    public GoogleSignInClient getGoogleSignInClient(){

        return authenticationRepository.getGoogleSignInClient();
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
