package com.example.foodplannerapp.details.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.model.Ingredient;
import com.example.foodplannerapp.data.model.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.example.foodplannerapp.utilis.NoInternetSnackBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DetailsFragment extends Fragment implements ViewInterface, NetworkListener {

    private ImageView imageView;
    private TextView mealName;
    private TextView mealArea;
    private TextView mealCategory;
    private WebView mealVideo;
    private RecyclerView recyclerView;
    private TextView instructions;
    private ArrayList<Ingredient> ingredientsList;
    private RecyclerViewAdapter myAdapter;
    private PresenterImpl presenter;
    private ImageView backIcon;
    private ImageView flagIcon;
    private ImageView favIcon;
    private boolean isFav = false;
    private FavoriteMealModel favMeal;
    private CalenderMealModel calMeal;
    private TextView showMore;
    private Button addToCalendarButton;
    private Meal meal;
    private Group internetGroup;
    private Group noLocalItem;
    private NetworkChangeListener networkChangeListener;
    private int mealID;


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
        backIcon = view.findViewById(R.id.backIcon);
        flagIcon = view.findViewById(R.id.flagIconDetails);
        favIcon = view.findViewById(R.id.favIcon);
        showMore = view.findViewById(R.id.showMore);
        internetGroup = view.findViewById(R.id.internetGroup);
        noLocalItem = view.findViewById(R.id.noLocalItem);
        addToCalendarButton = view.findViewById(R.id.addToCalendarBtn);
        calendarPermission();
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()),
                new MealsLocalDataSource(getContext())),
                FireStoreRepositoryImpl.getInstance(RemoteDatabaseServices.getInstance()), this);
        networkChangeListener = new NetworkChangeListener(this);
        mealID = DetailsFragmentArgs.fromBundle(getArguments()).getMealID();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), List.of());
        recyclerView.setAdapter(myAdapter);
        backIcon.setOnClickListener((v) -> Navigation.findNavController(view).navigateUp());
        favIcon.setOnClickListener((v) -> {
            if (presenter.getCurrentUser() != null) {
                if (meal != null || favMeal != null || calMeal != null) {
                    if (NetworkAvailability.isNetworkAvailable(getContext())) {
                        addToFavorite();
                    } else {
                        NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));

                    }
                }
            } else {
                showDialog(getString(R.string.you_can_t_add_meal_to_favorite_until_you_sign_in));
            }
        });
        addToCalendarButton.setOnClickListener(v -> {
            if (presenter.getCurrentUser() != null) {

                if (meal != null || favMeal != null || calMeal != null) {
                    if (NetworkAvailability.isNetworkAvailable(getContext())) {
                        addToCalendar();
                    } else {

                        NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));


                    }
                }
            } else {
                showDialog(getString(R.string.you_can_t_scheduler_the_meal_until_you_sign_in));
            }
        });

        if (presenter.getCurrentUser() != null) {
            presenter.getMealByIDFromFavorite(presenter.getCurrentUser().getUid(), String.valueOf(mealID));
            presenter.getMealByIDFromCalendar(presenter.getCurrentUser().getUid(), String.valueOf(mealID));

        } else {
            presenter.getMealByID(mealID);
        }
    }


    public void showInstructions(String mealInstructions) {
        if (mealInstructions.length() > 150) {
            instructions.setText(String.format("%s ....", mealInstructions.substring(0, 150)));
            showMore.setOnClickListener((v) -> {
                if (instructions.getText().length() < 160) {
                    instructions.setText(mealInstructions);
                    showMore.setText(R.string.show_less);
                } else {
                    instructions.setText(String.format("%s....", mealInstructions.substring(0, 150)));
                    showMore.setText(R.string.show_more);
                }
            });

        } else {
            instructions.setText(mealInstructions);
            showMore.setVisibility(View.GONE);
        }
    }

    public void updateRecyclerView(List<Ingredient> ingredients) {
        myAdapter.setIngredientList(ingredients);
        myAdapter.notifyDataSetChanged();
    }

    public void showDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(text);
        builder.setTitle(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.sign_in, (dialog, which) -> {
            navigateToLogin();
        });
        builder.setNegativeButton(R.string.continue_as_a_guest, (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_DetailsFragment_to_loginFragment, null,
                new NavOptions.Builder()
                        .setPopUpTo(R.id.favoriteFragment, true)
                        .setPopUpTo(R.id.calenderFragment, true)
                        .setPopUpTo(R.id.searchDetailsFragment, true)
                        .setPopUpTo(R.id.searchFragment, true)
                        .setPopUpTo(R.id.homeFragment, true)
                        .build());
    }

    public void addToCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.dialog_theme,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    CalenderMealModel calenderMeal = convertMealToCalendarMeal(selectedYear, selectedMonth, selectedDay);
                    presenter.addMealToMobileCalendar(selectedYear, selectedMonth, selectedDay, meal);
                    presenter.addMealToCalendar(calenderMeal);
                    presenter.addCalendarMealToFireStore(calenderMeal);
                    Snackbar snackbar = Snackbar
                            .make(requireView(), getString(R.string.meal_is_added_to_calendar), Snackbar.LENGTH_LONG).setActionTextColor(
                                    getResources().getColor(R.color.primaryColor)
                            ).setTextColor(getResources().getColor(R.color.white))
                            .setAction(R.string.undo, view2 -> {
                                presenter.deleteMealFromCalendar(calenderMeal);
                                presenter.deleteCalendarMealFromFireStore(calenderMeal);
                                presenter.deleteMealToMobileCalendar(selectedYear, selectedMonth, selectedDay, meal);

                            });


                    snackbar.show();
                },
                year, month, day);


        dialog.setOnShowListener(d -> {
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        });

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();


    }

    @NonNull
    private CalenderMealModel convertMealToCalendarMeal(int selectedYear, int selectedMonth, int selectedDay) {
        CalenderMealModel calenderMeal;
        if (meal != null) {
            calenderMeal = new CalenderMealModel(meal.getIdMeal(), selectedDay, selectedMonth + 1, selectedYear, presenter.getCurrentUser().getUid(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredientsList);

        } else {
            calenderMeal = new CalenderMealModel(favMeal.getIdMeal(), selectedDay, selectedMonth + 1, selectedYear, presenter.getCurrentUser().getUid(), favMeal.getStrMeal(), favMeal.getStrCategory(), favMeal.getStrArea(), favMeal.getStrInstructions(), favMeal.getStrMealThumb(), favMeal.getStrYoutube(), ingredientsList);

        }
        return calenderMeal;
    }

    public void addToFavorite() {

        if (!isFav) {
            favMeal = new FavoriteMealModel(meal.getIdMeal(), presenter.getCurrentUser().getUid(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredientsList);
            favIcon.setImageResource(R.drawable.baseline_favorite_24);
            presenter.addMealToFav(favMeal);
            presenter.addFavoriteMealToFireStore(favMeal);
            showSnackBar(getString(R.string.meal_is_added_to_favorite));
            isFav = true;

        } else {
            favIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            presenter.deleteMealFromFav(favMeal);
            presenter.deleteFavoriteMealFromFireStore(favMeal);
            showSnackBar(getString(R.string.meal_is_removed_from_favorite));
            isFav = false;
        }
    }

    private void calendarPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    1);
        }


    }

    public void loadFlagImage(String area) {
        if (isAdded()) {
            Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
            String countryCode = countryCodeMap.getOrDefault(area, "unknown");
            Glide.with(requireContext()).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(flagIcon);
        }
    }

    ArrayList<Ingredient> convertRoomList(List<Ingredient> ingredients) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.fromJson(gson.toJson(ingredients), type);
    }

    public void showSnackBar(String text) {
        if (getView() != null) {
            Snackbar snackbar = Snackbar
                    .make(requireView(), text, Snackbar.LENGTH_LONG)
                    .setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
        }
    }

    public void loadVideo(String youtubeUrl) {
        mealVideo.loadData(presenter.loadVideo(youtubeUrl), "text/html", "utf-8");
        mealVideo.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mealVideo.getSettings().setJavaScriptEnabled(true);
        mealVideo.setWebChromeClient(new WebChromeClient());
    }


    @Override
    public void onSuccess(Meal meal) {
        this.meal = meal;
        ingredientsList = presenter.getIngredients(meal);
        loadVideo(meal.getStrYoutube());
        mealName.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        showInstructions(meal.getStrInstructions());
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imageView);
        updateRecyclerView(ingredientsList);
        loadFlagImage(meal.getStrArea());


    }

    @Override
    public void onFavoriteDatabaseSuccess(List<FavoriteMealModel> favoriteMealModel) {

        if (!favoriteMealModel.isEmpty()) {
            favMeal = favoriteMealModel.get(0);
            isFav = true;
            favIcon.setImageResource(R.drawable.baseline_favorite_24);
            ingredientsList = convertRoomList(favMeal.getIngredients());
            loadVideo(favMeal.getStrYoutube());
            mealName.setText(favMeal.getStrMeal());
            mealArea.setText(favMeal.getStrArea());
            mealCategory.setText(favMeal.getStrCategory());
            showInstructions(favMeal.getStrInstructions());
            Glide.with(requireContext()).load(favMeal.getStrMealThumb()).into(imageView);
            updateRecyclerView(ingredientsList);
            loadFlagImage(favMeal.getStrArea());
        } else {
            presenter.getMealByID(mealID);
        }


    }

    @Override
    public void onCalendarDatabaseSuccess(List<CalenderMealModel> calenderMealModel) {
        if (calenderMealModel != null) {
            calMeal = calenderMealModel.get(0);
            ingredientsList = convertRoomList(calMeal.getIngredients());
            loadVideo(calMeal.getStrYoutube());
            mealName.setText(calMeal.getStrMeal());
            mealArea.setText(calMeal.getStrArea());
            mealCategory.setText(calMeal.getStrCategory());
            showInstructions(calMeal.getStrInstructions());
            Glide.with(getContext()).load(calMeal.getStrMealThumb()).into(imageView);
            updateRecyclerView(ingredientsList);
            loadFlagImage(calMeal.getStrArea());
        } else {
            presenter.getMealByID(mealID);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(networkChangeListener, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(networkChangeListener);
    }

    @Override
    public void onFavoriteMealSuccessfullyAddedToFireStore(String message) {

        Log.i("TAG", "onFavoriteMealAddedToFireStore: " + message);


    }

    @Override
    public void onFavoriteMealFailedToAddedToFireStore(String errorMessage) {

        Log.i("TAG", "onFavoriteMealFailedToAddedToFireStore: " + errorMessage);


    }

    @Override
    public void onFailure(String errorMessage) {
        showSnackBar(errorMessage);
        if (!NetworkAvailability.isNetworkAvailable(getContext()) && favMeal == null && calMeal == null) {
            noLocalItem.setVisibility(View.VISIBLE);
            internetGroup.setVisibility(View.GONE);

        }

    }

    @Override
    public void onLostConnection() {

        NoInternetSnackBar.showSnackBar(getView());


    }

    @Override
    public void onConnectionReturned() {
        noLocalItem.setVisibility(View.GONE);
        internetGroup.setVisibility(View.VISIBLE);
        presenter.getMealByID(mealID);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PresenterImpl.compositeDisposable.clear();
    }
}