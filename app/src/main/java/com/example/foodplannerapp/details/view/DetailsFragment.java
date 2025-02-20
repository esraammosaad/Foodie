package com.example.foodplannerapp.details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    ImageView imageView;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    WebView mealVideo;
    RecyclerView recyclerView;
    TextView instructions;
    List<Ingredient> ingredientsList;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    ImageView backIcon;


    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.detailsMealImage);
        mealName = view.findViewById(R.id.mealNameDetails);
        mealArea = view.findViewById(R.id.mealAreaDetails);
        mealCategory = view.findViewById(R.id.mealCategoryDetails);
        mealVideo = view.findViewById(R.id.webView);
        recyclerView = view.findViewById(R.id.detailsRecyclerView);
        instructions = view.findViewById(R.id.instructions);
        backIcon=view.findViewById(R.id.backIcon);
        presenter=PresenterImpl.getInstance(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(),new MealsLocalDataSource()));

        Meal meal = DetailsFragmentArgs.fromBundle(getArguments()).getMeal();
        ingredientsList= presenter.getIngredients(meal);

        mealName.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        instructions.setText(meal.getStrInstructions());
        Glide.with(this).load(meal.getStrMealThumb()).into(imageView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), ingredientsList);
        recyclerView.setAdapter(myAdapter);
        String youtubeUrl = meal.getStrYoutube();
        String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1);

        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" "
                + "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" "
                + "allowfullscreen></iframe>";
        mealVideo.loadData(video, "text/html", "utf-8");
        mealVideo.getSettings().setJavaScriptEnabled(true);
        mealVideo.setWebChromeClient(new WebChromeClient());
        backIcon.setOnClickListener((v)->{

            Navigation.findNavController(view).navigateUp();
        });


    }
}