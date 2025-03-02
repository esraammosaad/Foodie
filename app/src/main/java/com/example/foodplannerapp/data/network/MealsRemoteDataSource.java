package com.example.foodplannerapp.data.network;

import android.content.Context;

import com.example.foodplannerapp.data.model.AllAreasResponse;
import com.example.foodplannerapp.data.model.AllIngredientsResponse;
import com.example.foodplannerapp.data.model.GetAllCategoriesResponse;
import com.example.foodplannerapp.data.model.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.model.MealModel;
import com.example.foodplannerapp.utilis.NetworkAvailability;

import java.io.File;
import java.io.IOException;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
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
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                build();

        apiServices = retrofit.create(ApiServices.class);


    }


    public Single<MealModel> getRandomMeal() {
        return apiServices.getRandomMeal();
    }

    public Single<MealModel> getNewRandomMeal() {
        try {
            okHttpClient.cache().evictAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiServices.getRandomMeal();

    }

    public Single<MealModel> getMealsByFirstLetter() {
        return apiServices.getMealsByFirstLetter("c");

    }

    public Single<MealModel> getMealByID(int id) {
        return apiServices.getMealByID(id);

    }

    public Single<GetAllCategoriesResponse> getAllCategories() {
        return apiServices.getAllCategoriesWithPhotos();
    }

    public Single<AllAreasResponse> getAllAreas() {
       return apiServices.getAllAreas("list");
    }

    public Single<AllIngredientsResponse> getAllIngredients() {
        return apiServices.getAllIngredients("list");
    }

    public Single<GetMealsByFilterResponse> getAllMealsByCategory(String categoryName) {
        return apiServices.getMealSByCategory(categoryName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByArea(String areaName) {
        return apiServices.getMealSByArea(areaName);
    }

    public Single<GetMealsByFilterResponse> getAllMealsByIngredient(String ingredientName) {
        return apiServices.getMealSByIngredient(ingredientName);

    }


}
