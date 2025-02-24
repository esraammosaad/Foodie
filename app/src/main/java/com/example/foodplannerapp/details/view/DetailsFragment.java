package com.example.foodplannerapp.details.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DetailsFragment extends Fragment implements ViewInterface {

    ImageView imageView;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    WebView mealVideo;
    RecyclerView recyclerView;
    TextView instructions;
    ArrayList<Ingredient> ingredientsList;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    ImageView backIcon;
    ImageView flagIcon;
    ImageView favIcon;
    boolean isFav = false;
    FavoriteMealModel favMeal;
    TextView showMore;
    Button addToCalendarButton;
    Meal meal;
    Group internetGroup;
    Group noLocalItem;


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
        internetGroup=view.findViewById(R.id.internetGroup);
        noLocalItem=view.findViewById(R.id.noLocalItem);
        addToCalendarButton = view.findViewById(R.id.addToCalendarBtn);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()), new MealsLocalDataSource(getContext())), FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()), this);
        int mealID = DetailsFragmentArgs.fromBundle(getArguments()).getMealID();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), List.of());
        recyclerView.setAdapter(myAdapter);

        backIcon.setOnClickListener((v) -> {

            Navigation.findNavController(view).navigateUp();
        });
        favIcon.setOnClickListener((v) -> {

            if (presenter.getCurrentUser() != null) {

                if (NetworkAvailability.isNetworkAvailable(getContext())) {
                    addToFavorite();
                } else {

                    NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));


                }
            } else {

                showDialog(getString(R.string.you_can_t_add_meal_to_favorite_until_you_sign_in));


            }

        });
        addToCalendarButton.setOnClickListener(v -> {

            if (presenter.getCurrentUser() != null) {

                if (meal != null || favMeal != null) {
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
            loadMealsFromFavorite(mealID);

        } else {
            presenter.getMealByID(mealID);

        }

    }

    public void loadMealsFromFavorite(int mealID) {
        presenter.getAllFavoriteMeals(presenter.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<FavoriteMealModel>>() {
            @Override
            public void onChanged(List<FavoriteMealModel> favoriteMealModels) {
                FavoriteMealModel foundMeal = favoriteMealModels.stream()
                        .filter(meal -> meal.getIdMeal().equals(String.valueOf(mealID)))
                        .findFirst()
                        .orElse(null);

                if (foundMeal != null) {
                    isFav = true;
                    favMeal = foundMeal;
                    favIcon.setImageResource(R.drawable.baseline_favorite_24);
                    loadVideo(foundMeal.getStrYoutube());
                    mealName.setText(foundMeal.getStrMeal());
                    mealArea.setText(foundMeal.getStrArea());
                    mealCategory.setText(foundMeal.getStrCategory());
                    showInstructions(foundMeal.getStrInstructions());
                    ingredientsList = convertRoomList(foundMeal);
                    updateRecyclerView(ingredientsList);
                    loadFlagImage(foundMeal.getStrArea());

                } else {
                    presenter.getMealByID(mealID);

                }

            }
        });
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


    ArrayList<Ingredient> convertRoomList(FavoriteMealModel meal) {
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imageView);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gson.fromJson(gson.toJson(meal.getIngredients()), type);
    }

    public void updateRecyclerView(List<Ingredient> ingredientList) {

        myAdapter.setIngredientList(ingredientsList);
        myAdapter.notifyDataSetChanged();
    }

    public void showDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(text);
        builder.setTitle(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.sign_in, (DialogInterface.OnClickListener) (dialog, which) -> {

            Navigation.findNavController(getView()).navigate(R.id.action_DetailsFragment_to_loginFragment, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.homeFragment, true)
                            .build());

        });


        builder.setNegativeButton(R.string.continue_as_a_guest, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void addToCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.dialog_theme,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    CalenderMealModel calenderMeal = getCalenderMealModel(selectedYear, selectedMonth, selectedDay);

                    presenter.addMealToCalendar(calenderMeal);
                    presenter.insertCalendarMealToFireStore(calenderMeal);
                    Snackbar snackbar = Snackbar
                            .make(requireView(), "Meal is added to calendar", Snackbar.LENGTH_LONG).setActionTextColor(
                                    getResources().getColor(R.color.primaryColor)
                            ).setTextColor(getResources().getColor(R.color.white))
                            .setAction("UNDO", view2 -> {
                                presenter.deleteMealFromCalendar(calenderMeal);
                                presenter.deleteCalendarMealFromFireStore(calenderMeal);

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
    private CalenderMealModel getCalenderMealModel(int selectedYear, int selectedMonth, int selectedDay) {
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

    public void showSnackBar(String text) {
        Snackbar snackbar = Snackbar
                .make(requireView(), text, Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    public void loadVideo(String youtubeUrl) {
        String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"
                + videoId + "\" title=\"YouTube video player\" frameborder=\"0\" "
                + "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" "
                + "allowfullscreen></iframe>";
        mealVideo.loadData(video, "text/html", "utf-8");
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

    public void loadFlagImage(String area) {
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(area, "unknown");
        Glide.with(getContext()).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(flagIcon);
    }

    @Override
    public void onFavoriteMealAddedToFireStore(String message) {

        Log.i("TAG", "onFavoriteMealAddedToFireStore: " + message);


    }

    @Override
    public void onFavoriteMealFailedToAddedToFireStore(String errorMessage) {

        Log.i("TAG", "onFavoriteMealFailedToAddedToFireStore: " + errorMessage);


    }

    @Override
    public void onFailure(String errorMessage) {
        showSnackBar(errorMessage);

    }
}