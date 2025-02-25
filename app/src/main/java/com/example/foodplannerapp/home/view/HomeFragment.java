package com.example.foodplannerapp.home.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment implements ViewInterface, HomeListener, NetworkListener {

    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    ImageView randomMealImg;
    TextView randomMealName;
    TextView randomMealCategory;
    TextView randomMealArea;
    Button viewRecipeButton;
    Button refreshButton;
    ProgressBar progressBar;
    PresenterImpl presenter;
    Meal randomMeal;
    ImageView flagIcon;
    Group noInternetBanner;
    TextView dismiss;
    TextView turnWIFI;
    NetworkChangeListener networkChangeListener;

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        networkChangeListener = new NetworkChangeListener(this);

        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        randomMealImg = view.findViewById(R.id.randomMealImg);
        randomMealName = view.findViewById(R.id.randomMealNameText);
        randomMealCategory = view.findViewById(R.id.randomMealCategoryText);
        randomMealArea = view.findViewById(R.id.randomMealAreaText);
        refreshButton = view.findViewById(R.id.refreshButton);
        viewRecipeButton = view.findViewById(R.id.viewRecipeButton);
        flagIcon = view.findViewById(R.id.randomMealFlagIcon);
        progressBar = view.findViewById(R.id.progressBar);
        noInternetBanner = view.findViewById(R.id.noInternetBanner);
        dismiss=view.findViewById(R.id.dismiss);
        turnWIFI=view.findViewById(R.id.turnWIFI);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), Arrays.asList(), this);
        recyclerView.setAdapter(myAdapter);
        randomMealImg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()), new MealsLocalDataSource(getContext())), this);
        presenter.getMealsByFirstLetter();
        presenter.getRandomMeal();
        refreshButton.setOnClickListener((v) -> {
            if (NetworkAvailability.isNetworkAvailable(getContext())) {

                presenter.getNewRandomMeal();

            }else {

                noInternetBanner.setVisibility(View.VISIBLE);
            }

        });

        turnWIFI.setOnClickListener((v)->{

            WifiManager wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(true);
            wifi.reconnect();


        });

        dismiss.setOnClickListener((v)->{
            noInternetBanner.setVisibility(View.GONE);
        });

        viewRecipeButton.setOnClickListener((v) -> {
            if (randomMeal != null) {

                HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(Integer.parseInt(randomMeal.getIdMeal()));
                Navigation.findNavController(requireView()).navigate(action);
            }

        });

        randomMealImg.setOnClickListener((v)->{
            HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(Integer.parseInt(randomMeal.getIdMeal()));
            Navigation.findNavController(requireView()).navigate(action);


        });


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
        randomMeal = meal;
        randomMealName.setText(meal.getStrMeal());
        randomMealCategory.setText(meal.getStrCategory());
        randomMealArea.setText(meal.getStrArea());
        progressBar.setVisibility(View.GONE);
        randomMealImg.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(randomMealImg);
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(meal.getStrArea(), "unknown");
        Glide.with(requireContext()).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(flagIcon);

    }

    @Override
    public void getMealsByFirstLetter(List<Meal> mealList) {
        myAdapter.setMealsList(mealList);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(String errorMessage) {
//        Snackbar snackbar = Snackbar
//                .make(requireView(), errorMessage, Snackbar.LENGTH_LONG);
//        snackbar.setBackgroundTint(Color.rgb(60, 176, 67));
//        snackbar.show();


    }


    @Override
    public void onClickListener(Meal meal) {

        HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);


    }

    @Override
    public void onLostConnection() {

        noInternetBanner.setVisibility(View.VISIBLE);

    }

    @Override
    public void onConnectionReturned() {

        noInternetBanner.setVisibility(View.GONE);
        presenter.getMealsByFirstLetter();
        presenter.getRandomMeal();

    }
}