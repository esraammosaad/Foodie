package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.models.AllAreasResponse;
import com.example.foodplannerapp.data.models.AllIngredientsResponse;
import com.example.foodplannerapp.data.models.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.MealModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("random.php")
    Single<MealModel> getRandomMeal();
    @GET("search.php")
    Single<MealModel> getMealsByFirstLetter(@Query("f") String firstLetter);
    @GET("lookup.php")
    Single<MealModel>  getMealByID(@Query("i") Integer id);
    @GET("categories.php")
    Single<GetAllCategoriesResponse> getAllCategoriesWithPhotos();
    @GET("list.php")
    Single<AllAreasResponse> getAllAreas(@Query("a") String list);
    @GET("list.php")
    Single<AllIngredientsResponse> getAllIngredients(@Query("i") String list);
    @GET("filter.php")
    Single<GetMealsByFilterResponse> getMealSByCategory(@Query("c") String category);
    @GET("filter.php")
    Single<GetMealsByFilterResponse> getMealSByIngredient(@Query("i") String category);
    @GET("filter.php")
    Single<GetMealsByFilterResponse> getMealSByArea(@Query("a") String category);


}

