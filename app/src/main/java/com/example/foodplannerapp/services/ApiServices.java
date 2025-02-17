package com.example.foodplannerapp.services;

import com.example.foodplannerapp.models.AllCategoriesResponse;
import com.example.foodplannerapp.models.MealModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("random.php")
    Call<MealModel> getRandomMeal();

    @GET("search.php")
    Call<MealModel> getMealsByFirstLetter(@Query("f") String firstLetter);

    @GET("lookup.php")
    Call<MealModel> getMealByID(@Query("i") Integer id);

    @GET("list.php")
    Call<AllCategoriesResponse> getAllCategories(@Query("c") String list);

//    @GET("list.php")
//    Call<AllAreasResponse> getAllAreas(@Query("a") String list);
//
//    @GET("list.php")
//    Call<AllIngredientsResponse> getAllIngredients(@Query("i") String list);
//
//    @GET("filter.php")
//    Call<GetMealsByCategoryResponse> getMealSByCategory(@Query("c") String category);


}

