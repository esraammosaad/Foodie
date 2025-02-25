package com.example.foodplannerapp.search.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import com.example.foodplannerapp.data.models.Area;
import com.example.foodplannerapp.data.models.Category;
import com.example.foodplannerapp.data.models.IngredientMeal;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search.view.ViewInterface;
import com.example.foodplannerapp.utilis.SingleTransformation;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;

public class PresenterImpl {
    MealsRepositoryImpl mealsRepository;
    ViewInterface viewInterface;

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    @SuppressLint("CheckResult")
    public void getAllCategories() {

        mealsRepository.getAllCategories().
                compose(SingleTransformation.apply()).
                map(getAllCategoriesResponse -> getAllCategoriesResponse.getCategories()).
                subscribe(categoryList -> viewInterface.onSuccess(categoryList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getAllAreas() {

        mealsRepository.getAllAreas().
                compose(SingleTransformation.apply()).
                map(getAllAreasResponse -> getAllAreasResponse.getAreas()).
                subscribe(areaList -> viewInterface.onSuccess(areaList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getAllIngredients() {

        mealsRepository.getAllIngredients().
                compose(SingleTransformation.apply()).
                map(getAllIngredientsResponse -> getAllIngredientsResponse.getMeals()).
                subscribe(mealList -> viewInterface.onSuccess(mealList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void search(CharSequence s, List list) {

        if (!list.isEmpty() && list.get(0) instanceof Category) {

            Observable<Category> observable = Observable.fromIterable(list);
            observable.
                    filter(category -> category.
                            getStrCategory().
                            toLowerCase().
                            contains(String.valueOf(s))).
                    toList().
                    compose(SingleTransformation.apply()).
                    subscribe(
                            item -> viewInterface.onSearch(item),
                            throwable -> Log.i("TAG", "search: "+throwable.getMessage())
                    );
        } else if (!list.isEmpty() && list.get(0) instanceof Area) {
            Observable<Area> observable = Observable.fromIterable(list);
            observable.
                    filter(area -> area.
                            getStrArea().
                            toLowerCase().
                            contains(String.valueOf(s))).
                    toList().
                    compose(SingleTransformation.apply()).
                    subscribe(
                            item -> viewInterface.onSearch(item),
                            throwable -> Log.i("TAG", "search: "+throwable.getMessage())
                    );


        } else if (!list.isEmpty() && list.get(0) instanceof IngredientMeal) {

            Observable<IngredientMeal> observable = Observable.fromIterable(list);
            observable.
                    filter(ingredient -> ingredient.
                            getStrIngredient().
                            toLowerCase().
                            contains(String.valueOf(s))).
                    toList().
                    compose(SingleTransformation.apply()).
                    subscribe(
                            item -> viewInterface.onSearch(item),
                            throwable -> Log.i("TAG", "search: "+throwable.getMessage())
                    );
        }
    }


}
