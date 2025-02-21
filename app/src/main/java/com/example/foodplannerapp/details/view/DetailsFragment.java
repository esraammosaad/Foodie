package com.example.foodplannerapp.details.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.data.models.Ingredient;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.details.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.CountryCodeMapper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DetailsFragment extends Fragment {

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
        addToCalendarButton = view.findViewById(R.id.addToCalendarBtn);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), AuthenticationRepositoryImpl.getInstance(UserAuthentication.getInstance()));
        Meal meal = DetailsFragmentArgs.fromBundle(getArguments()).getMeal();
        ingredientsList = presenter.getIngredients(meal);
        loadVideo(meal);
        mealName.setText(meal.getStrMeal());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        if (meal.getStrInstructions().length() > 150) {
            instructions.setText(String.format("%s ....", meal.getStrInstructions().substring(0, 150)));
            showMore.setOnClickListener((v) -> {

                if (instructions.getText().length() < 160) {

                    instructions.setText(meal.getStrInstructions());
                    showMore.setText(R.string.show_less);
                } else {
                    instructions.setText(String.format("%s....", meal.getStrInstructions().substring(0, 150)));
                    showMore.setText(R.string.show_more);

                }
            });

        } else {
            instructions.setText(meal.getStrInstructions());
            showMore.setVisibility(View.GONE);
        }


        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imageView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), ingredientsList);
        recyclerView.setAdapter(myAdapter);

        backIcon.setOnClickListener((v) -> {

            Navigation.findNavController(view).navigateUp();
        });
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(meal.getStrArea(), "unknown");
        Glide.with(this).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(flagIcon);
        favMeal = new FavoriteMealModel(meal.getIdMeal(), presenter.getCurrentUser().getUid(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredientsList);
        presenter.getAllFavoriteMeals(UserAuthentication.getInstance().getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<FavoriteMealModel>>() {
            @Override
            public void onChanged(List<FavoriteMealModel> favoriteMealModels) {
                isFav = favoriteMealModels.stream().anyMatch(meal -> meal.getIdMeal().equals(favMeal.getIdMeal()));
                if (isFav) {
                    favIcon.setImageResource(R.drawable.baseline_favorite_24);

                }

            }
        });
        favIcon.setOnClickListener((v) -> {
            addToFavorite();

        });
        addToCalendarButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.dialog_theme,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay;
                        Toast.makeText(requireContext(), date, Toast.LENGTH_SHORT).show();
                    },
                    year, month, day);


            dialog.setOnShowListener(d -> {
                dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R. color. black));
                dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R. color. black));
            });

            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });


    }


    public void addToFavorite() {

        if (!isFav) {
            favIcon.setImageResource(R.drawable.baseline_favorite_24);
            presenter.addMealToFav(favMeal);
            Snackbar snackbar = Snackbar
                    .make(requireView(), "Meal is added to favorite", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
            isFav = true;

        } else {
            favIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            presenter.deleteMealFromFav(favMeal);
            Snackbar snackbar = Snackbar
                    .make(requireView(), "Meal is removed from favorite", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
            isFav = false;
        }
    }

    public void loadVideo(Meal meal) {
        String youtubeUrl = meal.getStrYoutube();
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


}