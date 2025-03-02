package com.example.foodplannerapp.search.presenter;

import android.util.Log;
import com.example.foodplannerapp.data.model.Area;
import com.example.foodplannerapp.data.model.Category;
import com.example.foodplannerapp.data.model.IngredientMeal;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search.view.ViewInterface;
import com.example.foodplannerapp.utilis.SingleTransformation;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class PresenterImpl {
    private final MealsRepositoryImpl mealsRepository;
    private final ViewInterface viewInterface;
    public static CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PresenterImpl(MealsRepositoryImpl mealsRepository, ViewInterface viewInterface) {
        this.mealsRepository = mealsRepository;
        this.viewInterface = viewInterface;
    }

    public void getAllCategories() {

        Disposable disposable=mealsRepository.getAllCategories().
                compose(SingleTransformation.apply()).
                map(getAllCategoriesResponse -> getAllCategoriesResponse.getCategories()).
                subscribe(categoryList -> viewInterface.onSuccess(categoryList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void getAllAreas() {

       Disposable disposable= mealsRepository.getAllAreas().
                compose(SingleTransformation.apply()).
                map(getAllAreasResponse -> getAllAreasResponse.getAreas()).
                subscribe(areaList -> viewInterface.onSuccess(areaList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
       compositeDisposable.add(disposable);
    }

    public void getAllIngredients() {

        Disposable disposable=mealsRepository.getAllIngredients().
                compose(SingleTransformation.apply()).
                map(getAllIngredientsResponse -> getAllIngredientsResponse.getMeals()).
                subscribe(mealList -> viewInterface.onSuccess(mealList),
                        throwable -> viewInterface.onFailure(throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void search(CharSequence s, List list) {

        if (!list.isEmpty() && list.get(0) instanceof Category) {

            Observable<Category> observable = Observable.fromIterable(list);
            Disposable disposable=observable.
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
            compositeDisposable.add(disposable);
        } else if (!list.isEmpty() && list.get(0) instanceof Area) {
            Observable<Area> observable = Observable.fromIterable(list);
            Disposable disposable=observable.
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
            compositeDisposable.add(disposable);


        } else if (!list.isEmpty() && list.get(0) instanceof IngredientMeal) {

            Observable<IngredientMeal> observable = Observable.fromIterable(list);
            Disposable disposable=observable.
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
            compositeDisposable.add(disposable);
        }
    }

}
