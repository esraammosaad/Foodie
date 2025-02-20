package com.example.foodplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplannerapp.data.models.AllCategoriesResponse;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.models.MealModel;
import com.example.foodplannerapp.view_model.HomeViewModel;
import com.example.foodplannerapp.view_model.MealDetailsViewModel;
import com.example.foodplannerapp.view_model.SearchViewModel;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle(R.string.app_name);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavBar);
        NavigationUI.setupWithNavController(bottomNav, navController);
        bottomNav.setVisibility(View.GONE);
//        actionBar.hide();
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.homeFragment || navDestination.getId() == R.id.searchFragment || navDestination.getId() == R.id.calenderFragment || navDestination.getId() == R.id.favoriteFragment || navDestination.getId() == R.id.profileFragment) {

                bottomNav.setVisibility(View.VISIBLE);
//                actionBar.show();

            } else {
                bottomNav.setVisibility(View.GONE);
//                actionBar.hide();
            }

        });


    }


}