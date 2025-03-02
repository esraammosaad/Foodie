package com.example.foodplannerapp.search_details.presenter;


import com.example.foodplannerapp.data.model.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.model.MealByFilter;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search_details.view.ViewInterface;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterImpl {

    private final MealsRepositoryImpl mealsRepository;
    private final ViewInterface viewInterface;

    public static CompositeDisposable compositeDisposable = new CompositeDisposable();


    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    public void getMealsByCategory(String categoryName) {

        Disposable disposable = mealsRepository.getAllMealsByCategory(categoryName).
                compose(apply()).
                subscribe(mealByFilters -> viewInterface.onSuccess(mealByFilters),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }

    public void getMealsByArea(String areaName) {

        Disposable disposable = mealsRepository.getAllMealsByArea(areaName).
                compose(apply()).
                subscribe(mealByFilters -> viewInterface.onSuccess(mealByFilters),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }

    public void getMealsByIngredient(String ingredientName) {

        Disposable disposable = mealsRepository.getAllMealsByIngredient(ingredientName).
                compose(apply()).
                subscribe(mealByFilters -> viewInterface.onSuccess(mealByFilters),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);

    }

    public void search(CharSequence s, List<MealByFilter> mealByFilterList) {
        Observable<MealByFilter> observable = Observable.fromIterable(mealByFilterList);
        Disposable disposable = observable.
                filter(meal -> meal.getStrMeal().toLowerCase().
                        startsWith(String.valueOf(s))).toList().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                        item -> {

                            viewInterface.onSearch(item);

                        }
                );
        compositeDisposable.add(disposable);
    }


    private SingleTransformer<GetMealsByFilterResponse, List<MealByFilter>> apply() {

        return upstream ->
                upstream.
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        map(getMealsByFilterResponse -> getMealsByFilterResponse.getMeals());


    }


}
