package com.example.foodplannerapp.favorite.view;

import com.example.foodplannerapp.data.local.model.FavoriteMealModel;

import java.util.List;

public interface ViewInterface {
    void onFavoriteListSuccess(List<FavoriteMealModel> list);

    void onSuccess(String message);
    void onFailure(String errorMessage);
}
