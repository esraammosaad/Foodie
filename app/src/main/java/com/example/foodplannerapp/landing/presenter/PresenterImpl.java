package com.example.foodplannerapp.landing.presenter;

import com.example.foodplannerapp.landing.data.repo.OnBoardingRepositoryImpl;
import com.google.firebase.auth.FirebaseUser;

public class PresenterImpl {

    private OnBoardingRepositoryImpl onBoardingRepository;
    private static PresenterImpl instance;

    private PresenterImpl(OnBoardingRepositoryImpl onBoardingRepository) {

        this.onBoardingRepository = onBoardingRepository;
    }

    public static PresenterImpl getInstance(OnBoardingRepositoryImpl onBoardingRepository) {

        if (instance == null) {
            instance = new PresenterImpl(onBoardingRepository);
        }
        return instance;
    }

    public void setOnOnBoardingState() {

        onBoardingRepository.setOnBoardingState();
    }

    public boolean getOnBoardingState() {

        return onBoardingRepository.getOnBoardingState();
    }

    public boolean getThemeState() {

        return onBoardingRepository.getThemeState();
    }

    public void setThemeState(boolean themeState) {

        onBoardingRepository.setThemeState(themeState);
    }

    public FirebaseUser getCurrentUser() {

        return onBoardingRepository.getCurrentUser();
    }

    public void signOut() {

        onBoardingRepository.signOut();
    }


}
