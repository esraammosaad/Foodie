package com.example.foodplannerapp.onboarding.presenter;

import com.example.foodplannerapp.onboarding.data.repo.OnBoardingRepositoryImpl;

public class PresenterImpl {

    OnBoardingRepositoryImpl onBoardingRepository;
    private static PresenterImpl instance;
    private PresenterImpl(OnBoardingRepositoryImpl onBoardingRepository){

        this.onBoardingRepository=onBoardingRepository;
    }

    public static PresenterImpl getInstance(OnBoardingRepositoryImpl onBoardingRepository){

        if(instance==null){
            instance=new PresenterImpl(onBoardingRepository);
        }
        return instance;
    }

    public void setOnOnBoardingState(){

        onBoardingRepository.setOnBoardingState();
    }
    public boolean getOnBoardingState(){

        return onBoardingRepository.getOnBoardingState();
    }

    public boolean getThemeState(){

        return onBoardingRepository.getThemeState();
    }


}
