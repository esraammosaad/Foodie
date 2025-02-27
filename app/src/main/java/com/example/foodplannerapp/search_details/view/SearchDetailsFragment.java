package com.example.foodplannerapp.search_details.view;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.models.MealByFilter;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.search_details.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;
import com.example.foodplannerapp.utilis.NoInternetSnackBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchDetailsFragment extends Fragment implements SearchDetailsListener, ViewInterface, NetworkListener {

    ImageView imageView;
    TextView textView;
    RecyclerView recyclerView;
    TextView resultsCount;
    EditText searchEditText;
    ImageView backIcon;
    RecyclerViewAdapter myAdapter;
    String selectedItem;
    PresenterImpl presenter;
    TextWatcher textWatcher;
    List<MealByFilter> mealByFilterList;
    ProgressBar progressBar;
    Group internetGroup;
    Group noInternetGroup;
    NetworkChangeListener networkChangeListener;
    TextView dismiss;
    TextView turnOnWIFI;


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
        progressBar=view.findViewById(R.id.progressBar4);
        internetGroup=view.findViewById(R.id.internetGroup);
        noInternetGroup=view.findViewById(R.id.noInternetGrup);
        dismiss=view.findViewById(R.id.dismiss2);
        turnOnWIFI=view.findViewById(R.id.turnWIFI2);
        networkChangeListener=new NetworkChangeListener(this);
        backIcon.setOnClickListener((v) -> {

            Navigation.findNavController(view).navigateUp();
        });
        recyclerView = view.findViewById(R.id.searchDetailsRecyclerView);
        textView.setText(SearchDetailsFragmentArgs.fromBundle(getArguments()).getName());
        Glide.with(getContext()).load(SearchDetailsFragmentArgs.fromBundle(getArguments()).getImage()).into(imageView);
        selectedItem = SearchDetailsFragmentArgs.fromBundle(getArguments()).getFilter();
        myAdapter = new RecyclerViewAdapter(getContext(), List.of(), this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
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

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("CheckResult")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (mealByFilterList != null) {
                    presenter.search(s, mealByFilterList);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        searchEditText.addTextChangedListener(textWatcher);
        dismiss.setOnClickListener((v)->{

            internetGroup.setVisibility(View.GONE);
        });
        turnOnWIFI.setOnClickListener((v)->{
            WifiManager wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
            wifi.setWifiEnabled(true);
            wifi.reconnect();

        });


    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(networkChangeListener, filter);     }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(networkChangeListener);

    }

    @Override
    public void onClickListener(MealByFilter meal) {

        SearchDetailsFragmentDirections.ActionSearchDetailsFragmentToDetailsFragment action =
                SearchDetailsFragmentDirections.actionSearchDetailsFragmentToDetailsFragment(Integer.parseInt(meal.getidMeal()));
        Navigation.findNavController(requireView()).navigate(action);


    }

    @Override
    public void onSuccess(List<MealByFilter> list) {
        this.mealByFilterList = list;
        myAdapter.setMealsList(list);
        myAdapter.notifyDataSetChanged();
        resultsCount.setText(myAdapter.getItemCount() + getString(R.string.results));
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onSearch(List<MealByFilter> list) {


        myAdapter.setMealsList(list);
        myAdapter.notifyDataSetChanged();
        resultsCount.setText(myAdapter.getItemCount() + getString(R.string.results));

    }

    @Override
    public void onFailure(String errorMessage) {
        if(!NetworkAvailability.isNetworkAvailable(getContext()) && mealByFilterList==null){

            noInternetGroup.setVisibility(View.VISIBLE);


        }
        progressBar.setVisibility(View.GONE);



    }

    @Override
    public void onLostConnection() {
        internetGroup.setVisibility(View.VISIBLE);
        NoInternetSnackBar.showSnackBar(requireView());


    }

    @Override
    public void onConnectionReturned() {
        internetGroup.setVisibility(View.GONE);
        noInternetGroup.setVisibility(View.GONE);


    }
}