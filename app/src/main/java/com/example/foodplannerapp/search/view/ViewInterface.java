package com.example.foodplannerapp.search.view;


import java.util.List;

public interface ViewInterface<T> {
    void onSearch(List<T> list);
    void onSuccess(List<T> list);
    void onFailure(String errorMessage);

}
