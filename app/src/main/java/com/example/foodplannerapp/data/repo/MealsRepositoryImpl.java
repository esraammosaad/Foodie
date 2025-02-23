package com.example.foodplannerapp.data.repo;


import androidx.lifecycle.LiveData;

import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


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

    public void getRandomMeal(NetworkCallBack networkCallBack) {


        mealsRemoteDataSource.getRandomMeal(networkCallBack);


    }

    public void getMealByID(NetworkCallBack networkCallBack, int id) {


        mealsRemoteDataSource.getMealByID(networkCallBack, id);


    }

    public void getMealsByFirstLetter(NetworkCallBack networkCallBack) {

        mealsRemoteDataSource.getMealsByFirstLetter(networkCallBack);


    }

    public void getAllCategories(NetworkCallBack networkCallBack) {

        mealsRemoteDataSource.getAllCategories(networkCallBack);

    }

    public void getAllAreas(NetworkCallBack networkCallBack) {

        mealsRemoteDataSource.getAllAreas(networkCallBack);
    }

    public void getAllIngredients(NetworkCallBack networkCallBack) {

        mealsRemoteDataSource.getAllIngredients(networkCallBack);
    }

    public void getAllMealsByCategory(NetworkCallBack networkCallBack, String categoryName) {
        mealsRemoteDataSource.getAllMealsByCategory(networkCallBack, categoryName);
    }

    public void getAllMealsByArea(NetworkCallBack networkCallBack, String areaName) {
        mealsRemoteDataSource.getAllMealsByArea(networkCallBack, areaName);
    }

    public void getAllMealsByIngredient(NetworkCallBack networkCallBack, String ingredientName) {
        mealsRemoteDataSource.getAllMealsByIngredient(networkCallBack, ingredientName);
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

    public void addMealToFavorite(FavoriteMealModel meal) {

        mealsLocalDataSource.addMealToDB(meal);
    }

    public void deleteMealFromFavorite(FavoriteMealModel meal) {

        mealsLocalDataSource.deleteMealFromDB(meal);
    }

    public LiveData<List<FavoriteMealModel>> getAllFavoriteMeals(String userUID) {
        return mealsLocalDataSource.getAllMeals(userUID);
    }

    public void addMealToCalender(CalenderMealModel meal) {


        mealsLocalDataSource.addMealToCalendar(meal);


    }

    public void deleteMealFromCalender(CalenderMealModel meal) {


        mealsLocalDataSource.deleteMealFromCalendar(meal);


    }

    public LiveData<List<CalenderMealModel>> getAllCalendarMeals(String userUID, int day, int month, int year) {

        return mealsLocalDataSource.getAllMealsFromCalendar(userUID, day, month, year);
    }


    public FirebaseUser getCurrentUser() {

        return AuthenticationServices.getInstance().getCurrentUser();
    }


}
