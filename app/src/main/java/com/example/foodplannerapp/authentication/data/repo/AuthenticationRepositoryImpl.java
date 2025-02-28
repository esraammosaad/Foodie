package com.example.foodplannerapp.authentication.data.repo;

import android.content.Context;

import androidx.activity.result.ActivityResult;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.network.database.GetDataFromFirebaseCallBack;
import com.example.foodplannerapp.utilis.SharedPreferencesManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepositoryImpl {

    private final AuthenticationServices authenticationServices;
    private final FiresStoreServices firesStoreServices;
    private static AuthenticationRepositoryImpl instance;

    private AuthenticationRepositoryImpl(AuthenticationServices authenticationServices, FiresStoreServices firesStoreServices) {
        this.authenticationServices = authenticationServices;
        this.firesStoreServices = firesStoreServices;
    }

    public static AuthenticationRepositoryImpl getInstance(AuthenticationServices authenticationServices, FiresStoreServices firesStoreServices) {

        if (instance == null) {
            instance = new AuthenticationRepositoryImpl(authenticationServices, firesStoreServices);
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

    public void getFavoriteMealsFromFireStore(GetDataFromFirebaseCallBack getDataFromFirebaseCallBack) {

        firesStoreServices.getFavoriteMealsFromFireStore(getCurrentUser().getUid(), getDataFromFirebaseCallBack);
    }

    public void getCalendarMealsFromFireStore(GetDataFromFirebaseCallBack getDataFromFirebaseCallBack) {

        firesStoreServices.getCalendarMealsFromFireStore(getCurrentUser().getUid(), getDataFromFirebaseCallBack);
    }

    public void saveThemeState(Context context, boolean state) {

        SharedPreferencesManager.getInstance(context).saveThemeState(state);

    }

    public boolean getThemeState(Context context) {

        return SharedPreferencesManager.getInstance(context).getThemeState();

    }


}
