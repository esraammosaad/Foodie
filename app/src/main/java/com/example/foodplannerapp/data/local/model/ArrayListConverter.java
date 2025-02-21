package com.example.foodplannerapp.data.local.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ArrayListConverter {
    @TypeConverter
    public String convertToJsonString(ArrayList array) {
        Gson gson = new Gson();
        return gson.toJson(array);
    }

    @TypeConverter
    public ArrayList convertToObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,ArrayList.class);
    }
}
