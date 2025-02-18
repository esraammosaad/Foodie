package com.example.foodplannerapp.data.network;

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

       apiServices=retrofit.create(ApiServices.class);



   }

   public void getRandomMeal(NetworkCallBack networkCallBack){
       Call<MealModel> call= apiServices.getRandomMeal();
       call.enqueue(new Callback<MealModel>() {
           @Override
           public void onResponse(Call<MealModel> call, Response<MealModel> response) {
               if(response.isSuccessful())
                networkCallBack.onSuccess(response.body().getMeals().get(0),null);
               else
                networkCallBack.onFailure(response.message());

           }

           @Override
           public void onFailure(Call<MealModel> call, Throwable t) {
               networkCallBack.onFailure(t.getMessage());

           }
       });


   }
   public void getMealsByFirstLetter(NetworkCallBack networkCallBack){
        Call<MealModel> call= apiServices.getMealsByFirstLetter("b");
        call.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if(response.isSuccessful())
                    networkCallBack.onSuccess(null,response.body().getMeals());
                else
                    networkCallBack.onFailure(response.message());

            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {
                networkCallBack.onFailure(t.getMessage());

            }
        });


    }




}
