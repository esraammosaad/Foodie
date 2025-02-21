package com.example.foodplannerapp.data.models;

import androidx.room.TypeConverters;

import com.example.foodplannerapp.data.local.model.IngredientDataTypeConverter;

@TypeConverters(IngredientDataTypeConverter.class)
public class Ingredient {

    private String image;
    private String ingredient;
    private String measure;

    public Ingredient() {
    }

    public Ingredient(String image, String ingredient, String measure) {
        this.image = image;
        this.ingredient = ingredient;
        this.measure = measure;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
