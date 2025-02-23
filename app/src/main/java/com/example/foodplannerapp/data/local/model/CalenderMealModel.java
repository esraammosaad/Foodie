package com.example.foodplannerapp.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.foodplannerapp.data.models.Ingredient;

import java.util.ArrayList;

@Entity(tableName = "calendar_meal_table", primaryKeys = {"idMeal", "day", "month", "year"})
public class CalenderMealModel {

    @NonNull
    private String idMeal;
    @NonNull
    private int day;
    @NonNull
    private int month;
    @NonNull
    private int year;
    private String userUID;
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strYoutube;
    @TypeConverters(ArrayListConverter.class)
    private ArrayList<Ingredient> ingredients;

    public CalenderMealModel() {
    }

    public CalenderMealModel(@NonNull String idMeal, int day, int month, int year, String userUID, String strMeal, String strCategory, String strArea, String strInstructions, String strMealThumb, String strYoutube, ArrayList<Ingredient> ingredients) {
        this.idMeal = idMeal;
        this.day = day;
        this.month = month;
        this.year = year;
        this.userUID = userUID;
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strYoutube = strYoutube;
        this.ingredients = ingredients;
    }

    public String getIdMeal() {
        return idMeal;
    }


    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

