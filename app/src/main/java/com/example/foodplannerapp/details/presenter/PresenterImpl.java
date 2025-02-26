package com.example.foodplannerapp.details.presenter;



import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.database.FireStoreCallBack;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.view.ViewInterface;
import com.example.foodplannerapp.utilis.CompletableTransformation;
import com.example.foodplannerapp.utilis.SingleTransformation;
import com.example.foodplannerapp.utilis.Transformation;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class PresenterImpl implements FireStoreCallBack {

    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();


    public PresenterImpl(MealsRepositoryImpl mealsRepository, FireStoreRepositoryImpl fireStoreRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.fireStoreRepository = fireStoreRepository;
        this.viewInterface = viewInterface;
    }


    public void getMealByID(int id) {

        Disposable disposable = mealsRepository.getMealByID(id).
                compose(SingleTransformation.apply()).
                map(mealModel -> mealModel.getMeals().get(0)).
                subscribe(meal -> viewInterface.onSuccess(meal),
                        throwable -> viewInterface.onFailure(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }


    public ArrayList<Ingredient> getIngredients(Meal meal) {
        return mealsRepository.getIngredientsList(meal);

    }

    public void addMealToFav(FavoriteMealModel meal) {

        Disposable disposable = mealsRepository.addMealToFavorite(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
        compositeDisposable.add(disposable);
    }

    public void deleteMealFromFav(FavoriteMealModel meal) {

        Disposable disposable = mealsRepository.deleteMealFromFavorite(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
        compositeDisposable.add(disposable);
    }


    public void addMealToCalendar(CalenderMealModel meal) {

        Disposable disposable = mealsRepository.addMealToCalender(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
        compositeDisposable.add(disposable);

    }

    public void deleteMealFromCalendar(CalenderMealModel meal) {

        Disposable disposable = mealsRepository.deleteMealFromCalender(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
        compositeDisposable.add(disposable);
    }


    public FirebaseUser getCurrentUser() {

        return mealsRepository.getCurrentUser();
    }

    public void getMealByIDFromFavorite(String userUID, String mealID) {
        Disposable disposable = mealsRepository.getMealByIDFromFavorite(userUID, mealID).
                compose(Transformation.apply()).
                subscribe(meal -> viewInterface.onFavoriteDatabaseSuccess(meal),
                        throwable -> viewInterface.onFavoriteDatabaseSuccess(List.of()));
        compositeDisposable.add(disposable);
    }

    public void getMealByIDFromCalendar(String userUID, String mealID) {
        Disposable disposable = mealsRepository.getMealByIDFromCalendar(userUID, mealID).
                compose(Transformation.apply()).
                subscribe(meal -> viewInterface.onCalendarDatabaseSuccess(meal),
                        throwable -> viewInterface.onFavoriteDatabaseSuccess(List.of()));

        compositeDisposable.add(disposable);
    }

    public void addFavoriteMealToFireStore(FavoriteMealModel meal) {

        fireStoreRepository.insertFavoriteMealToFireStore(meal, this);
    }

    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal) {

        fireStoreRepository.deleteFavoriteMealFromFireStore(meal, this);
    }

    public void insertCalendarMealToFireStore(CalenderMealModel meal) {

        fireStoreRepository.insertCalendarMealToFireStore(meal, this);

    }

    public void deleteCalendarMealFromFireStore(CalenderMealModel meal) {

        fireStoreRepository.deleteCalendarMealFromFireStore(meal, this);

    }

    @Override
    public void onFireStoreSuccess(String message) {
        viewInterface.onFavoriteMealAddedToFireStore(message);
    }

    @Override
    public void onFireStoreFailure(String errorMessage) {
        viewInterface.onFavoriteMealFailedToAddedToFireStore(errorMessage);

    }
}
