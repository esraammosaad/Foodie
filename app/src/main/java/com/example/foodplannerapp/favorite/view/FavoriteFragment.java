package com.example.foodplannerapp.favorite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.favorite.presenter.PresenterImpl;
import com.example.foodplannerapp.home.view.HomeFragmentDirections;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteListener, ViewInterface {

    RecyclerViewAdapter myAdapter;
    RecyclerView recyclerView;
    PresenterImpl presenter;
    LiveData<List<FavoriteMealModel>> mealList;


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
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()),this);
        mealList = presenter.getAllFavoriteMeals(presenter.getCurrentUser().getUid());
        mealList.observe(getViewLifecycleOwner(), new Observer<List<FavoriteMealModel>>() {
            @Override
            public void onChanged(List<FavoriteMealModel> favoriteMealModels) {

                myAdapter.setMealsList(favoriteMealModels);
                myAdapter.notifyDataSetChanged();


            }
        });


    }


    @Override
    public void onClickListener(FavoriteMealModel meal) {
        presenter.deleteMealFromFavorite(meal);
        presenter.deleteFavoriteMealFromFireStore(meal);
        Snackbar snackbar = Snackbar
                .make(requireView(), "Meal is removed from favorite", Snackbar.LENGTH_LONG).setActionTextColor(
                        getResources().getColor(R.color.primaryColor)
                ).setTextColor(getResources().getColor(R.color.white))
                .setAction("UNDO", view -> {
                    presenter.addMealToFavorite(meal);
                    presenter.insertCalendarMealToFireStore(meal);

                });


        snackbar.show();

    }

    @Override
    public void onItemClickListener(FavoriteMealModel meal) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToDetailsFragment action=
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);

    }

    @Override
    public void onSuccess(String message) {
        Log.i("TAG", "onSuccess: "+message);


    }

    @Override
    public void onFailure(String errorMessage) {
        Log.i("TAG", "onFailure: "+errorMessage);

    }
}