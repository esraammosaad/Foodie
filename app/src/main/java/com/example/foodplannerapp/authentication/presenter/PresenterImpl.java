package com.example.foodplannerapp.authentication.presenter;

import android.content.Context;

import androidx.activity.result.ActivityResult;

import com.example.foodplannerapp.authentication.data.network.AuthenticationCallBack;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.view.ViewInterface;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.database.GetDataFromFirebaseCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.utilis.CompletableTransformation;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PresenterImpl implements AuthenticationCallBack, GetDataFromFirebaseCallBack {
    private final AuthenticationRepositoryImpl authenticationRepository;
    private final MealsRepositoryImpl mealsRepository;
    private final ViewInterface viewInterface;


    public PresenterImpl(AuthenticationRepositoryImpl authenticationRepository,
                         MealsRepositoryImpl mealsRepository,
                         ViewInterface viewInterface) {
        this.authenticationRepository = authenticationRepository;
        this.mealsRepository=mealsRepository;
        this.viewInterface = viewInterface;

    }



    public void register(String email, String password, String name) {

        authenticationRepository.register(email, password, name, this);

    }

    public void login(String email, String password) {

        authenticationRepository.login(email, password, this);
    }
    public void forgetPassword(String email) {

        authenticationRepository.forgetPassword(email, this);
    }

    public void loginWithGoogle(ActivityResult result) throws ApiException {

        authenticationRepository.loginWithGoogle(result, this);


    }

    public GoogleSignInClient initGoogleSignIn(Context context) {

        return authenticationRepository.initGoogleSignIn(context);
    }

    public GoogleSignInClient getGoogleSignInClient() {

        return authenticationRepository.getGoogleSignInClient();
    }

    public FirebaseUser getCurrentUser() {

        return authenticationRepository.getCurrentUser();

    }

    public void getFavoriteMealsFromFireStore() {

        authenticationRepository.getFavoriteMealsFromFireStore(this);
    }

    public void getCalendarMealsFromFireStore() {

        authenticationRepository.getCalendarMealsFromFireStore(this);
    }

    @Override
    public void onSuccess(String message) {
        viewInterface.onSuccess(message);

    }

    @Override
    public void onFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }

    @Override
    public void onGetFavoriteMeals(QuerySnapshot mealsList) {
        for(QueryDocumentSnapshot meal : mealsList){
            mealsRepository.addMealToFavorite(meal.toObject(FavoriteMealModel.class)).
                    compose(CompletableTransformation.apply()).
                    subscribe();

        }

    }

    @Override
    public void onGetCalendarMeals(QuerySnapshot mealsList) {
        for(QueryDocumentSnapshot meal : mealsList){
            mealsRepository.
                    addMealToCalender(meal.toObject(CalenderMealModel.class)).
                    compose(CompletableTransformation.apply()).
                    subscribe();

        }

    }
}
