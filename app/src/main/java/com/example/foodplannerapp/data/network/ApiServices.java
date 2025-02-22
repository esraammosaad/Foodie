package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.models.AllAreasResponse;
import com.example.foodplannerapp.data.models.AllCategoriesResponse;
import com.example.foodplannerapp.data.models.AllIngredientsResponse;
import com.example.foodplannerapp.data.models.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.models.GetMealsByAreaResponse;
import com.example.foodplannerapp.data.models.GetMealsByCategoryResponse;
import com.example.foodplannerapp.data.models.GetMealsByIngredientResponse;
import com.example.foodplannerapp.data.models.MealModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("random.php")
    Call<MealModel> getRandomMeal();

    @GET("search.php")
    Call<MealModel> getMealsByFirstLetter(@Query("f") String firstLetter);

    @GET("lookup.php")
    Call<MealModel> getMealByID(@Query("i") Integer id);

    @GET("categories.php")
    Call<GetAllCategoriesResponse> getAllCategoriesWithPhotos();
    @GET("list.php")
    Call<AllCategoriesResponse> getAllCategories(@Query("c") String list);

    @GET("list.php")
    Call<AllAreasResponse> getAllAreas(@Query("a") String list);

    @GET("list.php")
    Call<AllIngredientsResponse> getAllIngredients(@Query("i") String list);

    @GET("filter.php")
    Call<GetMealsByCategoryResponse> getMealSByCategory(@Query("c") String category);
    @GET("filter.php")
    Call<GetMealsByIngredientResponse> getMealSByIngredient(@Query("i") String category);
    @GET("filter.php")
    Call<GetMealsByAreaResponse> getMealSByArea(@Query("a") String category);


}

