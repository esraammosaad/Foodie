package com.example.foodplannerapp.data.repo;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.AllAreasResponse;
import com.example.foodplannerapp.data.models.AllIngredientsResponse;
import com.example.foodplannerapp.data.models.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.models.MealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public class MealsRepositoryImpl {

    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;


    private static MealsRepositoryImpl instance;


    private MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (instance == null) {

            instance = new MealsRepositoryImpl(mealsRemoteDataSource, mealsLocalDataSource);
        }

        return instance;

    }

    public Single<MealModel> getRandomMeal() {
        return mealsRemoteDataSource.getRandomMeal();
    }

    public Single<MealModel> getNewRandomMeal() {
        return mealsRemoteDataSource.getNewRandomMeal();
    }

    public Single<MealModel> getMealByID(int id) {


        return mealsRemoteDataSource.getMealByID(id);


    }

    public Single<MealModel> getMealsByFirstLetter() {

        return mealsRemoteDataSource.getMealsByFirstLetter();


    }

    public Single<GetAllCategoriesResponse> getAllCategories() {

        return mealsRemoteDataSource.getAllCategories();

    }

    public Single<AllAreasResponse> getAllAreas() {

        return mealsRemoteDataSource.getAllAreas();
    }

    public Single<AllIngredientsResponse> getAllIngredients() {

        return mealsRemoteDataSource.getAllIngredients();
    }

    public Single<GetMealsByFilterResponse> getAllMealsByCategory(String categoryName) {
        return mealsRemoteDataSource.getAllMealsByCategory(categoryName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByArea(String areaName) {
        return mealsRemoteDataSource.getAllMealsByArea(areaName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByIngredient(String ingredientName) {
        return mealsRemoteDataSource.getAllMealsByIngredient(ingredientName);
    }

    public ArrayList<Ingredient> getIngredientsList(Meal meal) {
        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> measures = new ArrayList<>();
        ArrayList<Ingredient> ingredientsList = new ArrayList<>();
        ingredients.add(meal.getStrIngredient1());
        measures.add(meal.getStrMeasure1());
        ingredients.add(meal.getStrIngredient2());
        measures.add(meal.getStrMeasure2());
        ingredients.add(meal.getStrIngredient3());
        measures.add(meal.getStrMeasure3());
        ingredients.add(meal.getStrIngredient4());
        measures.add(meal.getStrMeasure4());
        ingredients.add(meal.getStrIngredient5());
        measures.add(meal.getStrMeasure5());
        ingredients.add(meal.getStrIngredient6());
        measures.add(meal.getStrMeasure6());
        ingredients.add(meal.getStrIngredient7());
        measures.add(meal.getStrMeasure7());
        ingredients.add(meal.getStrIngredient8());
        measures.add(meal.getStrMeasure8());
        ingredients.add(meal.getStrIngredient9());
        measures.add(meal.getStrMeasure9());
        ingredients.add(meal.getStrIngredient10());
        measures.add(meal.getStrMeasure10());
        ingredients.add(meal.getStrIngredient11());
        measures.add(meal.getStrMeasure11());
        ingredients.add(meal.getStrIngredient12());
        measures.add(meal.getStrMeasure12());
        ingredients.add(meal.getStrIngredient13());
        measures.add(meal.getStrMeasure13());
        ingredients.add(meal.getStrIngredient14());
        measures.add(meal.getStrMeasure14());
        ingredients.add(meal.getStrIngredient15());
        measures.add(meal.getStrMeasure15());
        ingredients.add(meal.getStrIngredient16());
        measures.add(meal.getStrMeasure16());
        ingredients.add(meal.getStrIngredient17());
        measures.add(meal.getStrMeasure17());
        ingredients.add(meal.getStrIngredient18());
        measures.add(meal.getStrMeasure18());
        ingredients.add(meal.getStrIngredient19());
        measures.add(meal.getStrMeasure19());
        ingredients.add(meal.getStrIngredient20());
        measures.add(meal.getStrMeasure20());
        for (int i = 0; i < ingredients.size(); i++) {

            if (ingredients.get(i) != null && measures.get(i) != null && !ingredients.get(i).isEmpty() && !measures.get(i).isEmpty()) {

                ingredientsList.add(new Ingredient("https://www.themealdb.com/images/ingredients/" + ingredients.get(i) + ".png", ingredients.get(i), measures.get(i)));

            }
        }


        return ingredientsList;
    }

    public Completable addMealToFavorite(FavoriteMealModel meal) {

        return mealsLocalDataSource.addMealToDB(meal);
    }

    public Completable deleteMealFromFavorite(FavoriteMealModel meal) {

        return mealsLocalDataSource.deleteMealFromDB(meal);
    }

    public Observable<List<FavoriteMealModel>> getAllFavoriteMeals(String userUID) {
        return mealsLocalDataSource.getAllMeals(userUID);
    }

    public Completable addMealToCalender(CalenderMealModel meal) {


        return mealsLocalDataSource.addMealToCalendar(meal);


    }

    public Completable deleteMealFromCalender(CalenderMealModel meal) {


       return mealsLocalDataSource.deleteMealFromCalendar(meal);


    }

    public Observable<List<CalenderMealModel>> getAllCalendarMeals(String userUID, int day, int month, int year) {

        return mealsLocalDataSource.getAllMealsFromCalendar(userUID, day, month, year);
    }

    public Observable<List<FavoriteMealModel>> getMealByIDFromFavorite(String userUID, String mealID) {
        return mealsLocalDataSource.getMealByIDFromFavorite(userUID, mealID);
    }

    public Observable<List<CalenderMealModel>> getMealByIDFromCalendar(String userUID, String mealID) {
        return mealsLocalDataSource.getMealByIDFromCalendar(userUID, mealID);
    }


    public FirebaseUser getCurrentUser() {

        return AuthenticationServices.getInstance().getCurrentUser();
    }


}
