package com.example.foodplannerapp.authentication.data.repo;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;

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

    public void register(String email , String password, String name,AuthenticationCallBack authenticationCallBack){
        userAuthentication.register(email, password, name,authenticationCallBack );

    }

    public void login(String email , String password, AuthenticationCallBack authenticationCallBack){

        userAuthentication.login(email, password, authenticationCallBack);

    }
    public void loginWithGoogle(ActivityResult result, AuthenticationCallBack authenticationCallBack) throws ApiException {



        userAuthentication.loginWithGoogle(result, authenticationCallBack);


    }

    public GoogleSignInClient initGoogleSignIn(Context context){

        return userAuthentication.initGoogleSignIn(context);
    }

    public GoogleSignInClient getGoogleSignInClient(){

        return userAuthentication.getGoogleSignInClient();
    }


    public FirebaseUser getCurrentUser(){

        return userAuthentication.getCurrentUser();
    }

    public void signOut(){

        userAuthentication.signOut();


    }



    }
