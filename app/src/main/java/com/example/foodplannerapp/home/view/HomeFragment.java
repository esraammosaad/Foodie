package com.example.foodplannerapp.home.view;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.NetworkCallBack;
import com.example.foodplannerapp.data.network.RetrofitFactory;
import java.util.Arrays;
import java.util.List;



public class HomeFragment extends Fragment implements NetworkCallBack {

    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    ImageView randomMealImg;
    TextView randomMealName;
    TextView randomMealCategory;
    TextView randomMealArea;
    Button viewRecipeButton;
    Button refreshButton;
    RetrofitFactory retrofitFactory;
    ProgressBar progressBar;

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
        myAdapter=new RecyclerViewAdapter(getContext(), Arrays.asList());
        recyclerView.setAdapter(myAdapter);
        randomMealImg.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        retrofitFactory=new RetrofitFactory();
        retrofitFactory.getRandomMeal(this);
        retrofitFactory.getMealsByFirstLetter(this);


        refreshButton.setOnClickListener((v)->{

            retrofitFactory.getRandomMeal(this);

        });


    }

    @Override
    public void onSuccess(Meal meal, List<Meal> mealList) {
        if(meal!=null){
                    randomMealName.setText(meal.getStrMeal());
                    randomMealCategory.setText(meal.getStrCategory());
                    randomMealArea.setText(meal.getStrArea());

            progressBar.setVisibility(View.GONE);
            randomMealImg.setVisibility(View.VISIBLE);
                   Glide.with(getContext()).load(meal.getStrMealThumb()).into(randomMealImg);

        }

        if(mealList!=null){
            myAdapter.setMealsList(mealList);
            myAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onFailure(String errorMessage) {

        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();

    }
}