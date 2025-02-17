package com.example.foodplannerapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

   static public Retrofit retrofit;
   public static ApiServices apiServices;
   public static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";


   static {


        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).
                build();

       apiServices=retrofit.create(ApiServices.class);



   }


}
