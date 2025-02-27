package com.example.foodplannerapp.favorite.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.favorite.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteListener, ViewInterface {

    private RecyclerViewAdapter myAdapter;
    private RecyclerView recyclerView;
    private PresenterImpl presenter;
    private LiveData<List<FavoriteMealModel>> mealList;
    private LottieAnimationView lottieAnimationView;
    private TextView emptyFavText;
    private Group guestGroup;
    private TextView continueAsAGuest;
    private TextView signInText;


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
        lottieAnimationView = view.findViewById(R.id.animationView);
        guestGroup = view.findViewById(R.id.guestView);
        emptyFavText = view.findViewById(R.id.emptyFavText);
        continueAsAGuest = view.findViewById(R.id.guest);
        signInText = view.findViewById(R.id.login);
        recyclerView = view.findViewById(R.id.favRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        myAdapter = new RecyclerViewAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        lottieAnimationView.setVisibility(View.VISIBLE);
        emptyFavText.setVisibility(View.VISIBLE);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(requireContext()),
                new MealsLocalDataSource(getContext())),
                FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()), this);
        if (presenter.getCurrentUser() != null) {
            presenter.getAllFavoriteMeals(presenter.getCurrentUser().getUid());

        } else {
            lottieAnimationView.setVisibility(View.VISIBLE);
            emptyFavText.setVisibility(View.VISIBLE);
            guestGroup.setVisibility(View.VISIBLE);


        }
        signInText.setOnClickListener((v) -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_favoriteFragment_to_loginFragment, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.favoriteFragment,true)
                            .setPopUpTo(R.id.homeFragment, true)
                            .build());
        });
        continueAsAGuest.setOnClickListener((v) -> {

            guestGroup.setVisibility(View.GONE);


        });


    }


    @Override
    public void onRemoveClickListener(FavoriteMealModel meal) {

        if(NetworkAvailability.isNetworkAvailable(requireContext())){
            presenter.deleteMealFromFavorite(meal);
            presenter.deleteFavoriteMealFromFireStore(meal);
            Snackbar snackbar = Snackbar
                    .make(requireView(), getString(R.string.meal_is_removed_from_favorite),
                            Snackbar.LENGTH_LONG).setActionTextColor(
                            getResources().getColor(R.color.primaryColor)
                    ).setTextColor(getResources().getColor(R.color.white))
                    .setAction("UNDO", view -> {
                        presenter.addMealToFavorite(meal);
                        presenter.insertCalendarMealToFireStore(meal);


                    });


            snackbar.show();
        }else{

            NoInternetDialog.showNoInternetDialog(getContext(),getString(R.string.no_internet_connection_please_reconnect_and_try_again));


        }


    }

    @Override
    public void onItemClickListener(FavoriteMealModel meal) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToDetailsFragment action =
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);

    }

    @Override
    public void onFavoriteListSuccess(List<FavoriteMealModel> list) {
        if (!list.isEmpty()) {

            lottieAnimationView.setVisibility(View.GONE);
            emptyFavText.setVisibility(View.GONE);
        } else {

            lottieAnimationView.setVisibility(View.VISIBLE);
            emptyFavText.setVisibility(View.VISIBLE);


        }
        myAdapter.setMealsList(list);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSuccess(String message) {
        Log.i("TAG", "onSuccess: " + message);


    }

    @Override
    public void onFailure(String errorMessage) {
        Log.i("TAG", "onFailure: " + errorMessage);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PresenterImpl.compositeDisposable.clear();
    }
}