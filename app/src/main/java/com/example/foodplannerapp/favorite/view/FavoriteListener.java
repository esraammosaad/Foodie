package com.example.foodplannerapp.favorite.view;

import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

public interface FavoriteListener {

    void onRemoveClickListener(FavoriteMealModel meal);
    void onItemClickListener(FavoriteMealModel meal);
}
