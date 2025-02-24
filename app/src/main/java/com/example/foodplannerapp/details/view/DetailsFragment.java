package com.example.foodplannerapp.details.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.foodplannerapp.MainActivity;
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
import com.google.android.material.snackbar.Snackbar;

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
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()), this);
        int mealID = DetailsFragmentArgs.fromBundle(getArguments()).getMealID();
        presenter.getMealByID(mealID);
        backIcon.setOnClickListener((v) -> {

            Navigation.findNavController(view).navigateUp();
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), List.of());
        recyclerView.setAdapter(myAdapter);
        favIcon.setOnClickListener((v) -> {

            if (presenter.getCurrentUser() != null) {

                if (meal != null)
                    addToFavorite();
            } else {
               showDialog(getString(R.string.you_can_t_add_meal_to_favorite_until_you_sign_in));


            }

        });
        addToCalendarButton.setOnClickListener(v -> {

            if(presenter.getCurrentUser()!=null){

            if (meal != null)
                addToCalendar(meal);
            }else {

                showDialog(getString(R.string.you_can_t_scheduler_the_meal_until_you_sign_in));

            }

        });

    }

    public void showDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(text);
        builder.setTitle(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.sign_in, (DialogInterface.OnClickListener) (dialog, which) -> {

            Navigation.findNavController(getView()).navigate(R.id.action_DetailsFragment_to_loginFragment,null,
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

    public void addToCalendar(Meal meal) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), R.style.dialog_theme,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    CalenderMealModel calenderMeal = new CalenderMealModel(meal.getIdMeal(), selectedDay, selectedMonth + 1, selectedYear, presenter.getCurrentUser().getUid(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredientsList);
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

    public void addToFavorite() {

        if (!isFav) {
            favIcon.setImageResource(R.drawable.baseline_favorite_24);
            presenter.addMealToFav(favMeal);
            presenter.addFavoriteMealToFireStore(favMeal);
            Snackbar snackbar = Snackbar
                    .make(requireView(), "Meal is added to favorite", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white));
            snackbar.show();
            isFav = true;

        } else {
            favIcon.setImageResource(R.drawable.baseline_favorite_border_24);
            presenter.deleteMealFromFav(favMeal);
            presenter.deleteFavoriteMealFromFireStore(favMeal);
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


    @Override
    public void onSuccess(Meal meal) {
        this.meal = meal;
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
        myAdapter.setIngredientList(ingredientsList);
        myAdapter.notifyDataSetChanged();
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(meal.getStrArea(), "unknown");
        Glide.with(this).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(flagIcon);
        if (presenter.getCurrentUser() != null) {
            favMeal = new FavoriteMealModel(meal.getIdMeal(), presenter.getCurrentUser().getUid(), meal.getStrMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrMealThumb(), meal.getStrYoutube(), ingredientsList);
            presenter.getAllFavoriteMeals(presenter.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<FavoriteMealModel>>() {
                @Override
                public void onChanged(List<FavoriteMealModel> favoriteMealModels) {
                    isFav = favoriteMealModels.stream().anyMatch(meal -> meal.getIdMeal().equals(favMeal.getIdMeal()));
                    if (isFav) {
                        favIcon.setImageResource(R.drawable.baseline_favorite_24);

                    }

                }
            });
        }

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
        Snackbar snackbar = Snackbar
                .make(getView(), errorMessage, Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white));
        snackbar.show();

    }
}