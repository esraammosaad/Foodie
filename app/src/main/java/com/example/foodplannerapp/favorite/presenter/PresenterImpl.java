package com.example.foodplannerapp.favorite.presenter;


import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseCallBack;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.favorite.view.ViewInterface;
import com.example.foodplannerapp.utilis.CompletableTransformation;
import com.example.foodplannerapp.utilis.Transformation;
import com.google.firebase.auth.FirebaseUser;


import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PresenterImpl implements RemoteDatabaseCallBack {
    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;
    public static CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PresenterImpl(MealsRepositoryImpl mealsRepository, FireStoreRepositoryImpl fireStoreRepository, ViewInterface viewInterface) {

        this.mealsRepository = mealsRepository;
        this.fireStoreRepository = fireStoreRepository;
        this.viewInterface = viewInterface;
    }

    public void getAllFavoriteMeals(String userUID) {
        Disposable disposable = mealsRepository.getAllFavoriteMeals(userUID).
                compose(Transformation.apply()).
                subscribe(favoriteMealModels -> viewInterface.onFavoriteListSuccess(favoriteMealModels));
        compositeDisposable.add(disposable);
    }

    public void deleteMealFromFavorite(FavoriteMealModel meal) {

        Disposable disposable = mealsRepository.deleteMealFromFavorite(meal).
                compose(CompletableTransformation.apply())
                .subscribe();
        compositeDisposable.add(disposable);

    }

    public void addMealToFavorite(FavoriteMealModel meal) {

        Disposable disposable = mealsRepository.addMealToFavorite(meal).
                compose(CompletableTransformation.apply()).
                subscribe();
        compositeDisposable.add(disposable);

    }

    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal) {

        fireStoreRepository.deleteFavoriteMealFromFireStore(meal, this);
    }

    public void insertCalendarMealToFireStore(FavoriteMealModel meal) {

        fireStoreRepository.insertFavoriteMealToFireStore(meal, this);

    }

    public FirebaseUser getCurrentUser() {

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
