package com.example.foodplannerapp.authentication.data.repo;

import android.content.Context;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;

public class AuthenticationRepositoryImpl {

    UserAuthentication userAuthentication;
    static private AuthenticationRepositoryImpl instance;

    private AuthenticationRepositoryImpl(UserAuthentication userAuthentication){
        this.userAuthentication=userAuthentication;
    }

    public static AuthenticationRepositoryImpl getInstance(UserAuthentication userAuthentication){

        if(instance==null){
            instance=new AuthenticationRepositoryImpl(userAuthentication);
        }

        return instance;

    }

    public void register(String email , String password, AuthenticationCallBack authenticationCallBack){
        userAuthentication.register(email, password, authenticationCallBack );

    }

    public void login(String email , String password, AuthenticationCallBack authenticationCallBack){

        userAuthentication.login(email, password, authenticationCallBack);

    }


}
