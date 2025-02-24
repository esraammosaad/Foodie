package com.example.foodplannerapp.data.local.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ArrayListConverter {
    @TypeConverter
    public String convertToJsonString(List array) {
        Gson gson = new Gson();
        return gson.toJson(array);
    }


    @TypeConverter
    public List convertToObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,List.class);
    }
}
