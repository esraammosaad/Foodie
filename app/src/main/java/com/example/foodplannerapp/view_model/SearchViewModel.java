package com.example.foodplannerapp.view_model;

import com.example.foodplannerapp.services.RetrofitFactory;
import com.example.foodplannerapp.models.AllCategoriesResponse;
import retrofit2.Call;

public class SearchViewModel {

    public Call<AllCategoriesResponse> getAllCategories(){


        Call<AllCategoriesResponse> mealModelCall= RetrofitFactory.apiServices.getAllCategories("list");

        return mealModelCall;
    }
}
