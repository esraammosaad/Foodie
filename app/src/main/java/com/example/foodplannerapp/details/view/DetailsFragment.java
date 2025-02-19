package com.example.foodplannerapp.details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;

import com.example.foodplannerapp.details.view.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailsFragment extends Fragment {

    ImageView imageView;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    VideoView mealVideo;
    RecyclerView recyclerView;
    TextView instructions;
    ArrayList<String> ingredients;
    ArrayList<String> measures;

    ArrayList<Ingredient> ingredientsList;
    RecyclerViewAdapter myAdapter;







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
        imageView=view.findViewById(R.id.detailsMealImage);
        mealName=view.findViewById(R.id.mealNameDetails);
        mealArea=view.findViewById(R.id.mealAreaDetails);
        mealCategory=view.findViewById(R.id.mealCategoryDetails);
        mealVideo=view.findViewById(R.id.videoView);
        recyclerView=view.findViewById(R.id.detailsRecyclerView);
        instructions=view.findViewById(R.id.instructions);

        Meal meal=DetailsFragmentArgs.fromBundle(getArguments()).getMeal();

        mealName.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        instructions.setText(meal.getStrInstructions());
        Glide.with(this).load(meal.getStrMealThumb()).into(imageView);
        ingredients=new ArrayList<>();
        measures=new ArrayList<>();
        ingredientsList=new ArrayList<>();

            ingredients.add(meal.getStrIngredient1());
            measures.add(meal.getStrMeasure1());
            ingredients.add(meal.getStrIngredient2());
            measures.add(meal.getStrMeasure2());
            ingredients.add(meal.getStrIngredient3());
            measures.add(meal.getStrMeasure3());
            ingredients.add(meal.getStrIngredient4());
            measures.add(meal.getStrMeasure4());
            ingredients.add(meal.getStrIngredient5());
            measures.add(meal.getStrMeasure5());
            ingredients.add(meal.getStrIngredient6());
            measures.add(meal.getStrMeasure6());
            ingredients.add(meal.getStrIngredient7());
            measures.add(meal.getStrMeasure7());
            ingredients.add(meal.getStrIngredient8());
            measures.add(meal.getStrMeasure8());
            ingredients.add(meal.getStrIngredient9());
            measures.add(meal.getStrMeasure9());
            ingredients.add(meal.getStrIngredient10());
            measures.add(meal.getStrMeasure10());
            ingredients.add(meal.getStrIngredient11());
            measures.add(meal.getStrMeasure11());
            ingredients.add(meal.getStrIngredient12());
            measures.add(meal.getStrMeasure12());
            ingredients.add(meal.getStrIngredient13());
            measures.add(meal.getStrMeasure13());
            ingredients.add(meal.getStrIngredient14());
            measures.add(meal.getStrMeasure14());
            ingredients.add(meal.getStrIngredient15());
            measures.add(meal.getStrMeasure15());
            ingredients.add(meal.getStrIngredient16());
            measures.add(meal.getStrMeasure16());
            ingredients.add(meal.getStrIngredient17());
            measures.add(meal.getStrMeasure17());
            ingredients.add(meal.getStrIngredient18());
            measures.add(meal.getStrMeasure18());
            ingredients.add(meal.getStrIngredient19());
            measures.add(meal.getStrMeasure19());
            ingredients.add(meal.getStrIngredient20());
            measures.add(meal.getStrMeasure20());
            for (int i = 0; i < ingredients.size(); i++) {

                if (ingredients.get(i) != null && measures.get(i) != null && !ingredients.get(i).isEmpty() && !measures.get(i).isEmpty()) {

                    ingredientsList.add(new Ingredient("https://www.themealdb.com/images/ingredients/"+ingredients.get(i)+".png",ingredients.get(i),measures.get(i)));
                    System.out.println(ingredientsList.get(i).getIngredient()+"=======================");

                }
            }

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter=new RecyclerViewAdapter(getContext(),ingredientsList);
        System.out.println(ingredientsList.size()+"==============");
        recyclerView.setAdapter(myAdapter);














    }
}