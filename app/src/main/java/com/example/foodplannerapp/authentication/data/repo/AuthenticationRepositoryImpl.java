package com.example.foodplannerapp.authentication.data.repo;

import android.content.Context;

import androidx.activity.result.ActivityResult;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseServices;
import com.example.foodplannerapp.data.network.database.GetDataFromRemoteDatabaseCallBack;
import com.example.foodplannerapp.data.local.LocalStorageDataSource;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepositoryImpl {

    private final AuthenticationServices authenticationServices;
    private final RemoteDatabaseServices remoteDatabaseServices;
    private final LocalStorageDataSource localStorageDataSource;
    private static AuthenticationRepositoryImpl instance;

    private AuthenticationRepositoryImpl(AuthenticationServices authenticationServices, RemoteDatabaseServices remoteDatabaseServices, LocalStorageDataSource localStorageDataSource) {
        this.authenticationServices = authenticationServices;
        this.remoteDatabaseServices = remoteDatabaseServices;
        this.localStorageDataSource = localStorageDataSource;
    }

    public static AuthenticationRepositoryImpl getInstance(AuthenticationServices authenticationServices, RemoteDatabaseServices remoteDatabaseServices, LocalStorageDataSource localStorageDataSource) {

        if (instance == null) {
            instance = new AuthenticationRepositoryImpl(authenticationServices, remoteDatabaseServices, localStorageDataSource);
        }

        return instance;

    }

    public void register(String email, String password, String name, AuthenticationCallBack authenticationCallBack) {
        authenticationServices.register(email, password, name, authenticationCallBack);

    }

    public void login(String email, String password, AuthenticationCallBack authenticationCallBack) {

        authenticationServices.login(email, password, authenticationCallBack);

    }

    public void forgetPassword(String email, AuthenticationCallBack authenticationCallBack) {

        authenticationServices.forgetPassword(email, authenticationCallBack);

    }

    public void loginWithGoogle(ActivityResult result, AuthenticationCallBack authenticationCallBack) throws ApiException {


        authenticationServices.loginWithGoogle(result, authenticationCallBack);


    }

    public GoogleSignInClient initGoogleSignIn(Context context) {

        return authenticationServices.initGoogleSignIn(context);
    }

    public GoogleSignInClient getGoogleSignInClient() {

        return authenticationServices.getGoogleSignInClient();
    }


    public FirebaseUser getCurrentUser() {

        return authenticationServices.getCurrentUser();
    }

    public void signOut() {

        authenticationServices.signOut();


    }

    public void getFavoriteMealsFromFireStore(GetDataFromRemoteDatabaseCallBack getDataFromRemoteDatabaseCallBack) {

        remoteDatabaseServices.getFavoriteMealsFromFireStore(getCurrentUser().getUid(), getDataFromRemoteDatabaseCallBack);
    }

    public void getCalendarMealsFromFireStore(GetDataFromRemoteDatabaseCallBack getDataFromRemoteDatabaseCallBack) {

        remoteDatabaseServices.getCalendarMealsFromFireStore(getCurrentUser().getUid(), getDataFromRemoteDatabaseCallBack);
    }

    public void saveThemeState(Context context, boolean state) {

        LocalStorageDataSource.getInstance(context).saveThemeState(state);

    }

    public boolean getThemeState() {

        return localStorageDataSource.getThemeState();

    }


}
