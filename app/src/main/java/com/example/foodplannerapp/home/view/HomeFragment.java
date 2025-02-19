package com.example.foodplannerapp.home.view;

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

import java.util.Arrays;
import java.util.List;



public class HomeFragment extends Fragment implements ViewInterface , Listener {

    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    ImageView randomMealImg;
    TextView randomMealName;
    TextView randomMealCategory;
    TextView randomMealArea;
    Button viewRecipeButton;
    Button refreshButton;
    Button viewRecipe;
    ProgressBar progressBar;
    PresenterImpl presenter;
    Meal randomMeal;

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
        recyclerView=view.findViewById(R.id.recyclerView);
        randomMealImg=view.findViewById(R.id.randomMealImg);
        randomMealName=view.findViewById(R.id.randomMealNameText);
        randomMealCategory=view.findViewById(R.id.randomMealCategoryText);
        randomMealArea=view.findViewById(R.id.randomMealAreaText);
        refreshButton=view.findViewById(R.id.refreshButton);
        viewRecipeButton=view.findViewById(R.id.viewRecipeButton);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        myAdapter=new RecyclerViewAdapter(getContext(), Arrays.asList(),this);
        recyclerView.setAdapter(myAdapter);
        randomMealImg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        presenter=PresenterImpl.getInstance( MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(),new MealsLocalDataSource()),this);
        presenter.getMealsByFirstLetter();
        presenter.getRandomMeal();




        refreshButton.setOnClickListener((v)->{

            presenter.getRandomMeal();

        });

        viewRecipeButton.setOnClickListener((v)->{
         if(randomMeal!=null){

             HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action=
                     HomeFragmentDirections.actionHomeFragmentToDetailsFragment(randomMeal);
             Navigation.findNavController(getView()).navigate(action);
         }

        });


    }


    @Override
    public void getRandomMeal(Meal meal) {
        randomMeal=meal;
        randomMealName.setText(meal.getStrMeal());
        randomMealCategory.setText(meal.getStrCategory());
        randomMealArea.setText(meal.getStrArea());
        progressBar.setVisibility(View.GONE);
        randomMealImg.setVisibility(View.VISIBLE);
        Glide.with(this).load(meal.getStrMealThumb()).into(randomMealImg);

    }

    @Override
    public void getMealsByFirstLetter(List<Meal> mealList) {
        myAdapter.setMealsList(mealList);
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(String errorMessage) {

    }


    @Override
    public void onClickListener(Meal meal) {

        HomeFragmentDirections.ActionHomeFragmentToDetailsFragment action=
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal);
        Navigation.findNavController(getView()).navigate(action);


    }
}