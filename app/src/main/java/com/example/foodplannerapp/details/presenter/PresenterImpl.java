package com.example.foodplannerapp.details.presenter;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import androidx.annotation.NonNull;
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
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class PresenterImpl implements FireStoreCallBack {

    MealsRepositoryImpl mealsRepository;
    FireStoreRepositoryImpl fireStoreRepository;
    ViewInterface viewInterface;
    public static CompositeDisposable compositeDisposable = new CompositeDisposable();


    public PresenterImpl(MealsRepositoryImpl mealsRepository,
                         FireStoreRepositoryImpl fireStoreRepository,
                         ViewInterface viewInterface) {

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

    public void addCalendarMealToFireStore(CalenderMealModel meal) {

        fireStoreRepository.insertCalendarMealToFireStore(meal, this);

    }

    public void deleteCalendarMealFromFireStore(CalenderMealModel meal) {

        fireStoreRepository.deleteCalendarMealFromFireStore(meal, this);

    }

    public void addMealToMobileCalendar(int year, int month, int day, Meal meal){
        mealsRepository.addMealToMobileCalendar(year, month, day, meal);

    }
    public void deleteMealToMobileCalendar(int year, int month, int day, Meal meal){
        mealsRepository.deleteMealToMobileCalendar(year, month, day, meal);

    }




    @Override
    public void onFireStoreSuccess(String message) {
        viewInterface.onFavoriteMealSuccessfullyAddedToFireStore(message);
    }

    @Override
    public void onFireStoreFailure(String errorMessage) {
        viewInterface.onFavoriteMealFailedToAddedToFireStore(errorMessage);

    }

    public String loadVideo(String youtubeUrl) {
        String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" "
                + "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" "
                + "allowfullscreen></iframe>";

        return video;

    }


    public FirebaseUser getCurrentUser() {

        return mealsRepository.getCurrentUser();
    }
}
