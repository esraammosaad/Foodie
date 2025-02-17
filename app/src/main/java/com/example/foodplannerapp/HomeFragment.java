package com.example.foodplannerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.models.Meal;
import com.example.foodplannerapp.models.MealModel;
import com.example.foodplannerapp.view_model.HomeViewModel;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    HomeViewModel homeViewModel;
    RecyclerViewAdapter myAdapter;
    Meal meal;
    Call<MealModel> call;
    Call<MealModel> call1;
    ImageView randomMealImg;
    TextView randomMealName;
    TextView randomMealCategory;
    TextView randomMealArea;
    Button viewRecipeButton;
    Button refreshButton;








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
        super.onViewCreated(view, savedInstanceState);
        homeViewModel=new HomeViewModel();
        recyclerView=view.findViewById(R.id.recyclerView);
        randomMealImg=view.findViewById(R.id.randomMealImg);
        randomMealName=view.findViewById(R.id.randomMealNameText);
        randomMealCategory=view.findViewById(R.id.randomMealCategoryText);
        randomMealArea=view.findViewById(R.id.randomMealAreaText);
        refreshButton=view.findViewById(R.id.refreshButton);
        viewRecipeButton=view.findViewById(R.id.viewRecipeButton);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter=new RecyclerViewAdapter(getContext(), Arrays.asList());
        recyclerView.setAdapter(myAdapter);
        randomMealImg.setImageResource(R.drawable.chefhat);
        call=homeViewModel.getRandomMeal();
        call.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if(response.isSuccessful()){
                    meal=response.body().getMeals().get(0);
                    randomMealName.setText(meal.getStrMeal());
                    randomMealCategory.setText(meal.getStrCategory());
                    randomMealArea.setText(meal.getStrArea());
                    Glide.with(getContext()).load(meal.getStrMealThumb()).placeholder(R.drawable.chefhat).into(randomMealImg);

                }





            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {


            }
        });
        refreshButton.setOnClickListener((v)->{
            call=homeViewModel.getRandomMeal();
            call.enqueue(new Callback<MealModel>() {
                @Override
                public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                    if(response.isSuccessful()){
                        meal=response.body().getMeals().get(0);
                        randomMealName.setText(meal.getStrMeal());
                        randomMealCategory.setText(meal.getStrCategory());
                        randomMealArea.setText(meal.getStrArea());
                        Glide.with(getContext()).load(meal.getStrMealThumb()).placeholder(R.drawable.chefhat).into(randomMealImg);

                    }





                }

                @Override
                public void onFailure(Call<MealModel> call, Throwable t) {


                }
            });
        });

        call1=homeViewModel.getMealsByFirstLetter("a");
        call1.enqueue(new Callback<MealModel>() {
            @Override
            public void onResponse(Call<MealModel> call, Response<MealModel> response) {
                if(response.isSuccessful()){

                    myAdapter.setMealsList(response.body().getMeals());
                    myAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<MealModel> call, Throwable t) {

            }
        });
    }
}