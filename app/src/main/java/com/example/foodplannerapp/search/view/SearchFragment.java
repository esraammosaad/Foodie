package com.example.foodplannerapp.search.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.Area;
import com.example.foodplannerapp.data.models.Category;
import com.example.foodplannerapp.data.models.IngredientMeal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;
import java.util.Map;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchListener, ViewInterface, NetworkListener {

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
    TextWatcher textWatcher;
    List list;
    Group noInternetGroup;
    NetworkChangeListener networkChangeListener;
    TextView dismiss;
    TextView turnWIFI;
    ImageView noWifiImg;
    TextView noInternetText;



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
        networkChangeListener=new NetworkChangeListener(this);
        selectedItem = view.findViewById(R.id.selectedItemText);
        filter = view.findViewById(R.id.filterIcon);
        filterIcon = view.findViewById(R.id.filterSmallIcon);
        searchField = view.findViewById(R.id.searchTextField);
        noInternetGroup=view.findViewById(R.id.internetGroup);
        turnWIFI=view.findViewById(R.id.turnWIFI2);
        dismiss=view.findViewById(R.id.dismiss2);
        noWifiImg=view.findViewById(R.id.noWifiImg);
        noInternetText=view.findViewById(R.id.noInternet);
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
                if(NetworkAvailability.isNetworkAvailable(getContext())){
                    if (item.equals(getString(R.string.categories))) {

                        presenter.getAllCategories();


                    } else if (item.equals(getString(R.string.areas))) {
                        presenter.getAllAreas();

                    } else {
                        presenter.getAllIngredients();



                    }


                }else{

                    noInternetGroup.setVisibility(View.VISIBLE);
                    Snackbar snackbar = Snackbar
                            .make(requireView(), "No Internet Connection", Snackbar.LENGTH_LONG).
                            setTextColor(getResources().getColor(R.color.white));
                    snackbar.show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()), new MealsLocalDataSource(getContext())), this);
        presenter.getAllCategories();
        searchField.setHint("Search Category");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter = new RecyclerViewCategoryAdapter(getContext(), List.of(), this);
        areaAdapter = new RecyclerViewAreaAdapter(getContext(), List.of(), this);
        ingredientAdapter = new RecyclerViewIngredientAdapter(getContext(), List.of(), this);
        recyclerView.setAdapter(categoryAdapter);
        list = List.of();
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("CheckResult")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (!list.isEmpty() && list.get(0) instanceof Category) {
                    Observable<Category> observable = Observable.fromIterable(list);
                    observable.
                            filter(category -> category.getStrCategory().toLowerCase().
                                    contains(String.valueOf(s))).toList().
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(
                                    item -> {

                                        categoryAdapter.setCategoryList(item);
                                        categoryAdapter.notifyDataSetChanged();

                                    }
                            );
                } else if (!list.isEmpty() && list.get(0) instanceof Area) {
                    Observable<Area> observable = Observable.fromIterable(list);
                    observable.
                            filter(area -> area.getStrArea().toLowerCase().
                                    contains(String.valueOf(s))).toList().
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(
                                    item -> {

                                        areaAdapter.setAreaList(item);
                                        areaAdapter.notifyDataSetChanged();

                                    }
                            );


                } else if (!list.isEmpty() && list.get(0) instanceof IngredientMeal) {

                    Observable<IngredientMeal> observable = Observable.fromIterable(list);
                    observable.
                            filter(ingredient -> ingredient.getStrIngredient().toLowerCase().
                                    contains(String.valueOf(s))).toList().
                            subscribeOn(Schedulers.io()).
                            observeOn(AndroidSchedulers.mainThread()).
                            subscribe(
                                    item -> {

                                        ingredientAdapter.setIngredientList(item);
                                        ingredientAdapter.notifyDataSetChanged();

                                    }
                            );
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        searchField.addTextChangedListener(textWatcher);
        dismiss.setOnClickListener((v)->{

            noInternetGroup.setVisibility(View.GONE);
        });
        turnWIFI.setOnClickListener((v)->{
            WifiManager wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(true);
            wifi.reconnect();

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(networkChangeListener, filter);    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(networkChangeListener);
    }

    @Override
    public void onSuccess(List list) {
        this.list = list;
        if (list.get(0) instanceof Category) {
            searchField.setHint("Search Category");
            selectedItem.setText(R.string.categories);
            categoryAdapter.setCategoryList(list);
            recyclerView.setAdapter(categoryAdapter);


        } else if (list.get(0) instanceof Area) {

            searchField.setHint("Search Area");
            selectedItem.setText(R.string.areas);
            areaAdapter.setAreaList(list);
            recyclerView.setAdapter(areaAdapter);

        } else {
            searchField.setHint("Search Ingredient");
            selectedItem.setText(R.string.ingredients);
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
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((Category) item).getStrCategory(), ((Category) item).getStrCategoryThumb(), getString(R.string.categories));


        } else if (item instanceof IngredientMeal) {
            action =
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((IngredientMeal) item).getStrIngredient(), "https://www.themealdb.com/images/ingredients/" + ((IngredientMeal) item).getStrIngredient() + ".png", getString(R.string.ingredients));


        } else {
            Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
            String countryCode = countryCodeMap.getOrDefault(((Area) item).getStrArea(), "unknown");
            action =
                    SearchFragmentDirections.actionSearchFragmentToSearchDetailsFragment(((Area) item).getStrArea(), "https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png", getString(R.string.areas));


        }
        Navigation.findNavController(requireView()).navigate(action);


    }

    @Override
    public void onLostConnection() {
        noInternetGroup.setVisibility(View.VISIBLE);
        if(list.isEmpty()){
            noInternetText.setVisibility(View.VISIBLE);
            noWifiImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionReturned() {
        noInternetGroup.setVisibility(View.GONE);
        presenter.getAllCategories();

    }
}