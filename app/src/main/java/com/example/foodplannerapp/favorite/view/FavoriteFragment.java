package com.example.foodplannerapp.favorite.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.MealLocalModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.favorite.presenter.PresenterImpl;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteListener {

    RecyclerViewAdapter myAdapter;
    RecyclerView recyclerView;
    PresenterImpl presenter;
    LiveData<List<MealLocalModel>> mealList;


    public FavoriteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        myAdapter = new RecyclerViewAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())));
        mealList = presenter.getAllFavoriteMeals(UserAuthentication.getInstance().getCurrentUser().getUid());
        mealList.observe(getViewLifecycleOwner(), new Observer<List<MealLocalModel>>() {
            @Override
            public void onChanged(List<MealLocalModel> mealLocalModels) {

                myAdapter.setMealsList(mealLocalModels);
                myAdapter.notifyDataSetChanged();


            }
        });


    }


    @Override
    public void onClickListener(MealLocalModel meal) {
        presenter.deleteMealFromFavorite(meal);
        Snackbar snackbar = Snackbar
                .make(requireView(), "Meal is removed from favorite", Snackbar.LENGTH_LONG).setActionTextColor(
                        getResources().getColor(R.color.primaryColor)
                ).setTextColor(getResources().getColor(R.color.white))
                .setAction("UNDO", view -> {
                    presenter.addMealToFavorite(meal);

                });


        snackbar.show();

    }
}