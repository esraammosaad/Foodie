package com.example.foodplannerapp.search_details.view;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.models.MealByFilter;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.view.HomeFragmentDirections;
import com.example.foodplannerapp.search_details.presenter.PresenterImpl;

import java.util.Arrays;
import java.util.List;


public class SearchDetailsFragment extends Fragment implements SearchDetailsListener, ViewInterface {

    ImageView imageView;
    TextView textView;
    RecyclerView recyclerView;
    TextView resultsCount;
    EditText searchEditText;
    ImageView backIcon;
    RecyclerViewAdapter myAdapter;
    String selectedItem;
    PresenterImpl presenter;


    public SearchDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.searchDetailsImage);
        textView = view.findViewById(R.id.mealNameDetails);
        resultsCount = view.findViewById(R.id.countOfResults);
        searchEditText = view.findViewById(R.id.searchTextFieldDetails);
        backIcon = view.findViewById(R.id.backIconSearch);
        backIcon.setOnClickListener((v) -> {

            Navigation.findNavController(view).navigateUp();
        });
        recyclerView = view.findViewById(R.id.searchDetailsRecyclerView);
        textView.setText(SearchDetailsFragmentArgs.fromBundle(getArguments()).getName());
        Glide.with(getContext()).load(SearchDetailsFragmentArgs.fromBundle(getArguments()).getImage()).into(imageView);
        selectedItem = SearchDetailsFragmentArgs.fromBundle(getArguments()).getFilter();

        myAdapter = new RecyclerViewAdapter(getContext(), List.of(), this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()), new MealsLocalDataSource(getContext())), this);
        if (selectedItem.equals(getString(R.string.categories))) {
            presenter.getMealsByCategory(textView.getText().toString());

        } else if (selectedItem.equals(getString(R.string.areas))) {
            presenter.getMealsByArea(textView.getText().toString());


        } else {

            presenter.getMealsByIngredient(textView.getText().toString());
        }


    }

    @Override
    public void onClickListener(MealByFilter meal) {

        SearchDetailsFragmentDirections.ActionSearchDetailsFragmentToDetailsFragment action=
                SearchDetailsFragmentDirections.actionSearchDetailsFragmentToDetailsFragment(Integer.parseInt(meal.getidMeal()));
        Navigation.findNavController(requireView()).navigate(action);


    }

    @Override
    public void onSuccess(List<MealByFilter> list) {
        myAdapter.setMealsList(list);
        myAdapter.notifyDataSetChanged();
        resultsCount.setText(myAdapter.getItemCount() + getString(R.string.results));

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}