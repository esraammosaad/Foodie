package com.example.foodplannerapp.data.network;

import com.example.foodplannerapp.data.models.AllAreasResponse;
import com.example.foodplannerapp.data.models.AllIngredientsResponse;
import com.example.foodplannerapp.data.models.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.models.GetMealsByAreaResponse;
import com.example.foodplannerapp.data.models.GetMealsByCategoryResponse;
import com.example.foodplannerapp.data.models.GetMealsByIngredientResponse;
import com.example.foodplannerapp.data.models.MealModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {

    static public Retrofit retrofit;
    public static ApiServices apiServices;
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";


    public MealsRemoteDataSource() {


        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        apiServices = retrofit.create(ApiServices.class);


    }

    public void getRandomMeal(NetworkCallBack networkCallBack) {
        Call<MealModel> call = apiServices.getRandomMeal();
        call.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(response.body().getMeals().get(0), null);
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getMealsByFirstLetter(NetworkCallBack networkCallBack) {
        Call<MealModel> call = apiServices.getMealsByFirstLetter("b");
        call.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }
    public void getMealByID(NetworkCallBack networkCallBack , int id){
        Call<MealModel> call = apiServices.getMealByID(id);
        call.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllCategories(NetworkCallBack networkCallBack) {
        Call<GetAllCategoriesResponse> call = apiServices.getAllCategoriesWithPhotos();
        call.enqueue(new Callback<GetAllCategoriesResponse>() {
            @Override
            public void onResponse(Call<GetAllCategoriesResponse> call, Response<GetAllCategoriesResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getCategories());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetAllCategoriesResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllAreas(NetworkCallBack networkCallBack) {
        Call<AllAreasResponse> call = apiServices.getAllAreas("list");
        call.enqueue(new Callback<AllAreasResponse>() {
            @Override
            public void onResponse(Call<AllAreasResponse> call, Response<AllAreasResponse> response) {

                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getAreas());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<AllAreasResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllIngredients(NetworkCallBack networkCallBack) {
        Call<AllIngredientsResponse> call = apiServices.getAllIngredients("list");
        call.enqueue(new Callback<AllIngredientsResponse>() {
            @Override
            public void onResponse(Call<AllIngredientsResponse> call, Response<AllIngredientsResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<AllIngredientsResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByCategory(NetworkCallBack networkCallBack, String categoryName) {
        Call<GetMealsByCategoryResponse> call = apiServices.getMealSByCategory(categoryName);
        call.enqueue(new Callback<GetMealsByCategoryResponse>() {
            @Override
            public void onResponse(Call<GetMealsByCategoryResponse> call, Response<GetMealsByCategoryResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByCategoryResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByArea(NetworkCallBack networkCallBack, String areaName) {
        Call<GetMealsByAreaResponse> call = apiServices.getMealSByArea(areaName);
        call.enqueue(new Callback<GetMealsByAreaResponse>() {
            @Override
            public void onResponse(Call<GetMealsByAreaResponse> call, Response<GetMealsByAreaResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByAreaResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByIngredient(NetworkCallBack networkCallBack, String ingredientName) {
        Call<GetMealsByIngredientResponse> call = apiServices.getMealSByIngredient(ingredientName);
        call.enqueue(new Callback<GetMealsByIngredientResponse>() {
            @Override
            public void onResponse(Call<GetMealsByIngredientResponse> call, Response<GetMealsByIngredientResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByIngredientResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }


}
