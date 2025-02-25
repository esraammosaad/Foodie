package com.example.foodplannerapp.search.presenter;

import android.annotation.SuppressLint;

import com.example.foodplannerapp.data.models.Area;
import com.example.foodplannerapp.data.models.Category;
import com.example.foodplannerapp.data.models.IngredientMeal;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search.view.ViewInterface;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PresenterImpl implements NetworkCallBack {
    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    public void getAllCategories() {

        mealsRepository.getAllCategories(this);
    }

    public void getAllAreas() {

        mealsRepository.getAllAreas(this);
    }

    public void getAllIngredients() {

        mealsRepository.getAllIngredients(this);
    }

    @SuppressLint("CheckResult")
    public void search(CharSequence s, List list) {

        if (!list.isEmpty() && list.get(0) instanceof Category) {

            Observable<Category> observable = Observable.fromIterable(list);
            observable.
                    filter(category -> category.getStrCategory().toLowerCase().
                            contains(String.valueOf(s))).toList().
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(
                            item -> {

                                viewInterface.onSearch(item);

                            }
                    );
        } else if (!list.isEmpty() && list.get(0) instanceof Area) {
            Observable<Area> observable = Observable.fromIterable(list);
            observable.
                    filter(area -> area.getStrArea().toLowerCase().
                            contains(String.valueOf(s))).toList().
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(
                            item -> {

                                viewInterface.onSearch(item);

                            }
                    );


        } else if (!list.isEmpty() && list.get(0) instanceof IngredientMeal) {

            Observable<IngredientMeal> observable = Observable.fromIterable(list);
            observable.
                    filter(ingredient -> ingredient.getStrIngredient().toLowerCase().
                            contains(String.valueOf(s))).toList().
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(
                            item -> {

                                viewInterface.onSearch(item);

                            }
                    );
        }
    }


    @Override
    public void onSuccess(Meal meal, List list) {

        viewInterface.onSuccess(list);

    }

    @Override
    public void onFailure(String errorMessage) {

        viewInterface.onFailure(errorMessage);

    }
}
