package com.example.foodplannerapp.search.view;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.Area;
import com.example.foodplannerapp.data.models.Category;
import com.example.foodplannerapp.data.models.IngredientMeal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.home.view.HomeFragmentDirections;
import com.example.foodplannerapp.search.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment implements SearchListener, ViewInterface {

    TextView selectedItem;
    View filter;
    ImageView filterIcon;
    EditText searchField;
    RecyclerView recyclerView;
    RecyclerViewCategoryAdapter categoryAdapter;
    RecyclerViewAreaAdapter areaAdapter;
    RecyclerViewIngredientAdapter ingredientAdapter;
    PresenterImpl presenter;
    Spinner spinner;


    public SearchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedItem = view.findViewById(R.id.selectedItemText);
        filter = view.findViewById(R.id.filterIcon);
        filterIcon = view.findViewById(R.id.filterSmallIcon);
        searchField = view.findViewById(R.id.searchTextField);
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        spinner = view.findViewById(R.id.spinner);
        String[] items = {getString(R.string.categories), getString(R.string.ingredients), getString(R.string.areas)};

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>
                (getContext(), R.layout.spinner_item, items) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setVisibility(View.GONE);
                return view;
            }
        };

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals(getString(R.string.categories))) {
                    presenter.getAllCategories();
                    searchField.setHint("Search Category");
                    selectedItem.setText(R.string.categories);

                } else if (item.equals(getString(R.string.areas))) {
                    presenter.getAllAreas();
                    searchField.setHint("Search Area");
                    selectedItem.setText(R.string.areas);
                } else {
                    presenter.getAllIngredients();
                    searchField.setHint("Search Ingredient");
                    selectedItem.setText(R.string.ingredients);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
        presenter.getAllCategories();
        searchField.setHint("Search Category");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new RecyclerViewCategoryAdapter(getContext(), List.of(), this);
        areaAdapter = new RecyclerViewAreaAdapter(getContext(), List.of(), this);
        ingredientAdapter = new RecyclerViewIngredientAdapter(getContext(), List.of(), this);
        recyclerView.setAdapter(categoryAdapter);
    }


    @Override
    public void onSuccess(List list) {
        if (list.get(0) instanceof Category) {
            categoryAdapter.setCategoryList(list);
            recyclerView.setAdapter(categoryAdapter);


        } else if (list.get(0) instanceof Area) {


            areaAdapter.setAreaList(list);
            recyclerView.setAdapter(areaAdapter);

        } else {

            ingredientAdapter.setIngredientList(list);
            recyclerView.setAdapter(ingredientAdapter);


        }

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onClickListener(Object item) {
        SearchFragmentDirections.ActionSearchFragmentToSearchDetailsFragment action;


        if (item instanceof Category) {

            action =
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((Category) item).getStrCategory(), ((Category) item).getStrCategoryThumb(),getString(R.string.categories));


        } else if (item instanceof IngredientMeal) {
            action =
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((IngredientMeal) item).getStrIngredient(), "https://www.themealdb.com/images/ingredients/" + ((IngredientMeal) item).getStrIngredient() + ".png",getString(R.string.ingredients));


        } else {
            Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
            String countryCode = countryCodeMap.getOrDefault(((Area) item).getStrArea(), "unknown");
            action =
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((Area) item).getStrArea(), "https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png",getString(R.string.areas));


        }
        Navigation.findNavController(requireView()).navigate(action);


    }
}