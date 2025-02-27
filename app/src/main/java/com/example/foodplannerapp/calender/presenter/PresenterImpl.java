package com.example.foodplannerapp.calender.presenter;

import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.network.database.FireStoreCallBack;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.view.ViewInterface;
import com.example.foodplannerapp.utilis.CompletableTransformation;
import com.example.foodplannerapp.utilis.Transformation;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PresenterImpl implements FireStoreCallBack {

    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;
    static public CompositeDisposable compositeDisposable = new CompositeDisposable();


    public PresenterImpl(MealsRepositoryImpl mealsRepository,FireStoreRepositoryImpl fireStoreRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.fireStoreRepository=fireStoreRepository;
        this.viewInterface=viewInterface;
    }

    public void getAllMealsFromCalendar(String userUID, int day , int month , int year) {

       Disposable  disposable= mealsRepository.getAllCalendarMeals(userUID, day , month,year).
                 compose(Transformation.apply()).
                 subscribe(calenderMealModels -> viewInterface.onGetCalendarListFromDatabase(calenderMealModels),
                         throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }

    public void deleteMealFromCalendar(CalenderMealModel meal){

        Disposable disposable=mealsRepository.deleteMealFromCalender(meal).
                compose(CompletableTransformation.apply()).
                subscribe(() -> getAllMealsFromCalendar(getCurrentUser().getUid(),meal.getDay(),meal.getMonth(),meal.getYear()));
        compositeDisposable.add(disposable);
    }
    public void addMealToCalendar(CalenderMealModel meal){

       Disposable disposable= mealsRepository.addMealToCalender(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
       compositeDisposable.add(disposable);
    }


    public void deleteCalendarMealFromFireStore(CalenderMealModel meal){

        fireStoreRepository.deleteCalendarMealFromFireStore(meal,this);
    }

    public void insertCalendarMealToFireStore(CalenderMealModel meal) {

        fireStoreRepository.insertCalendarMealToFireStore(meal, this);

    }

    public FirebaseUser getCurrentUser(){

        return mealsRepository.getCurrentUser();
    }

    @Override
    public void onFireStoreSuccess(String message) {
        viewInterface.onSuccess(message);

    }

    @Override
    public void onFireStoreFailure(String errorMessage) {
        viewInterface.onFailure(errorMessage);

    }
}
