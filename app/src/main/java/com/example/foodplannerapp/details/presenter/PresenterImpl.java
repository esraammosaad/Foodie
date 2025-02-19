package com.example.foodplannerapp.details.presenter;

import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import java.util.List;

public class PresenterImpl {

    MealsRepositoryImpl mealsRepository;

    private static PresenterImpl instance = null;

    private PresenterImpl(MealsRepositoryImpl mealsRepository) {

        this.mealsRepository = mealsRepository;
    }

    public static PresenterImpl getInstance(MealsRepositoryImpl mealsRepository) {

        if (instance == null) {

            instance = new PresenterImpl(mealsRepository);
        }

        return instance;

    }

   public List<Ingredient> getIngredients(Meal meal){
        return  mealsRepository.getIngredientsList(meal);

    }
}
