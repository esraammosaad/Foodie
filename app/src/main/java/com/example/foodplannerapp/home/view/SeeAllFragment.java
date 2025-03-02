package com.example.foodplannerapp.home.view;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.model.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;
import com.example.foodplannerapp.utilis.NoInternetSnackBar;

import java.util.List;


public class SeeAllFragment extends Fragment implements ViewInterface, HomeListener, NetworkListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter myAdapter;
    private PresenterImpl presenter;
    private ImageView backIcon;
    private NetworkChangeListener networkChangeListener;


    public SeeAllFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_see_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.seeAllRecyclerView);
        backIcon = view.findViewById(R.id.backIcon);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), List.of(), this);
        recyclerView.setAdapter(myAdapter);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(requireContext()),
                new MealsLocalDataSource(getContext())), this);
        presenter.getMealsByFirstLetter();
        backIcon.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        networkChangeListener = new NetworkChangeListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(networkChangeListener, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(networkChangeListener);
    }

    @Override
    public void getRandomMeal(Meal meal) {

    }

    @Override
    public void getMealsByFirstLetter(List<Meal> mealList) {
        myAdapter.setMealsList(mealList);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(String errorMessage) {
        if (!NetworkAvailability.isNetworkAvailable(getContext())) {
            NoInternetSnackBar.showSnackBar(requireView());

        }

    }

    @Override
    public void onClickListener(Meal meal) {
        SeeAllFragmentDirections.ActionSeeAllFragmentToDetailsFragment action =
                SeeAllFragmentDirections.actionSeeAllFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);

    }

    @Override
    public void onLostConnection() {
        NoInternetSnackBar.showSnackBar(requireView());


    }

    @Override
    public void onConnectionReturned() {

    }
}