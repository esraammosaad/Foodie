package com.example.foodplannerapp.data.network;

import android.content.Context;

import com.example.foodplannerapp.data.models.AllAreasResponse;
import com.example.foodplannerapp.data.models.AllIngredientsResponse;
import com.example.foodplannerapp.data.models.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.MealModel;
import com.example.foodplannerapp.utilis.NetworkAvailability;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSource {

    static public Retrofit retrofit;
    public static ApiServices apiServices;
    public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    OkHttpClient okHttpClient;


    public MealsRemoteDataSource(Context context) {



        int cacheSize = 10 * 10 * 1024;
        Cache cache = new Cache(new File(context.getCacheDir(), "http_cache"), cacheSize);
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient().
                newBuilder().
                cache(cache).
                addInterceptor(chain -> {

                    Request request = chain.request();
                    if (!NetworkAvailability.isNetworkAvailable(context)) {
                        request = request.newBuilder().
                                header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                                .build();
                    }
                    return chain.proceed(request);
                }).addNetworkInterceptor(chain -> {
                    okhttp3.Response response=chain.proceed(chain.request());
                    return response.newBuilder().header("Cache-Control", "public, max-age=1800").build();

                }).addInterceptor(loggingInterceptor).build();


        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                client(okHttpClient).
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

    public void getNewRandomMeal(NetworkCallBack networkCallBack) {
        try {
            okHttpClient.cache().evictAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void getMealByID(NetworkCallBack networkCallBack, int id) {
        Call<MealModel> call = apiServices.getMealByID(id);
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
                if (response.isSuccessful()) {
                    System.out.println(response.body().getMeals().get(0).getStrIngredient() + "======================iiii");
                    networkCallBack.onSuccess(null, response.body().getMeals());
                } else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<AllIngredientsResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByCategory(NetworkCallBack networkCallBack, String categoryName) {
        Call<GetMealsByFilterResponse> call = apiServices.getMealSByCategory(categoryName);
        call.enqueue(new Callback<GetMealsByFilterResponse>() {
            @Override
            public void onResponse(Call<GetMealsByFilterResponse> call, Response<GetMealsByFilterResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByFilterResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByArea(NetworkCallBack networkCallBack, String areaName) {
        Call<GetMealsByFilterResponse> call = apiServices.getMealSByArea(areaName);
        call.enqueue(new Callback<GetMealsByFilterResponse>() {
            @Override
            public void onResponse(Call<GetMealsByFilterResponse> call, Response<GetMealsByFilterResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByFilterResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }

    public void getAllMealsByIngredient(NetworkCallBack networkCallBack, String ingredientName) {
        Call<GetMealsByFilterResponse> call = apiServices.getMealSByIngredient(ingredientName);
        call.enqueue(new Callback<GetMealsByFilterResponse>() {
            @Override
            public void onResponse(Call<GetMealsByFilterResponse> call, Response<GetMealsByFilterResponse> response) {
                if (response.isSuccessful())
                    networkCallBack.onSuccess(null, response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<GetMealsByFilterResponse> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }


}
