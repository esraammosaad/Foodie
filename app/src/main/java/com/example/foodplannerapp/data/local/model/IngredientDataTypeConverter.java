package com.example.foodplannerapp.data.local.model;

import androidx.room.TypeConverter;
import com.example.foodplannerapp.data.model.Ingredient;
import com.google.gson.Gson;

public class IngredientDataTypeConverter {
    @TypeConverter
    public String convertToJsonString(Ingredient ingredient) {
        Gson gson = new Gson();
        return gson.toJson(ingredient);
    }

    @TypeConverter
    public Ingredient convertToObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Ingredient.class);
    }
}
