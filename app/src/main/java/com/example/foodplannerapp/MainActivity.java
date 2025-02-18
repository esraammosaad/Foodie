package com.example.foodplannerapp;

import android.os.Bundle;
import android.view.View;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    MealDetailsViewModel mealDetailsViewModel;
    SearchViewModel searchViewModel;
    Meal meal;
    Call<MealModel> call;
    Call<AllCategoriesResponse> callCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//         homeViewModel=new HomeViewModel();
//         mealDetailsViewModel=new MealDetailsViewModel();
//         searchViewModel=new SearchViewModel();
//         call=homeViewModel.getRandomMeal();
//         call.enqueue(new Callback<MealModel>() {
//            @Override
//            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
//                meal=response.body().getMeals().get(0);
//                System.out.println(response.body().getMeals().get(0).getStrMeal()+"=================");
//                System.out.println(meal.toString());
//                call=mealDetailsViewModel.getMealDetails(Integer.parseInt(meal.getIdMeal()));
//                call.enqueue(new Callback<MealModel>() {
//                    @Override
//                    public void onResponse(Call<MealModel> call, Response<MealModel> response) {
//                        System.out.println(response.body().getMeals().get(0).getIdMeal()+"hhh");
//                    }
//
//                    @Override
//                    public void onFailure(Call<MealModel> call, Throwable t) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<MealModel> call, Throwable t) {
//
//                System.out.println("=========================");
//
//            }
//        });
//         call=homeViewModel.getMealsByFirstLetter("a");
//         call.enqueue(new Callback<MealModel>() {
//             @Override
//             public void onResponse(Call<MealModel> call, Response<MealModel> response) {
//                 System.out.println(response.body().getMeals().get(2).getIdMeal().toString()+"list===");
//             }
//
//             @Override
//             public void onFailure(Call<MealModel> call, Throwable t) {
//
//             }
//         });
//        callCategories=searchViewModel.getAllCategories();
//        callCategories.enqueue(new Callback<AllCategoriesResponse>() {
//            @Override
//            public void onResponse(Call<AllCategoriesResponse> call, Response<AllCategoriesResponse> response) {
//                System.out.println(response.body().getMeals().get(0).getStrCategory()+"ccc");
//            }
//
//            @Override
//            public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
//
//            }
//        });

    }
}