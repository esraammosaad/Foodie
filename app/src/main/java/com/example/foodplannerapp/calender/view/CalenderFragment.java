package com.example.foodplannerapp.calender.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.disposables.Disposable;


public class CalenderFragment extends Fragment implements CalendarListener, ViewInterface {

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    TextView textCalendar;
    int mealDay = 0;
    int mealMonth = 0;
    int mealYear = 0;
    Calendar calendar;
    Group guestGroup;
    TextView continueAsAGuest;
    TextView signInText;
    Disposable mealDisposable;


    public CalenderFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.calendarRecyclerView);
        textCalendar = view.findViewById(R.id.textCalenderView);
        guestGroup = view.findViewById(R.id.calendarGuestGroup);
        continueAsAGuest = view.findViewById(R.id.guest);
        signInText = view.findViewById(R.id.login);
        presenter = new PresenterImpl(
                MealsRepositoryImpl.getInstance(
                        new MealsRemoteDataSource(getContext()),
                        new MealsLocalDataSource(getContext())),
                FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()), this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerViewAdapter(getContext(), List.of(), this);
        recyclerView.setAdapter(myAdapter);
        calendar = Calendar.getInstance();
        mealDay = calendar.get(Calendar.DAY_OF_MONTH);
        mealMonth = calendar.get(Calendar.MONTH) + 1;
        mealYear = calendar.get(Calendar.YEAR);

        if (presenter.getCurrentUser() != null) {


            mealDisposable=presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(), mealDay, mealMonth, mealYear);
            calendar.set(Calendar.MONTH, mealMonth - 1);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            textCalendar.setText(getString(R.string.today_s_picks) + monthFormat.format(calendar.getTime()) + " " + mealDay + ", " + mealYear);


            calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                mealDay = dayOfMonth;
                mealMonth = month + 1;
                mealYear = year;loadMeals(mealDay, mealMonth, mealYear);


                calendar.set(Calendar.MONTH, month);
                if (mealDay == calendar.get(Calendar.DAY_OF_MONTH) && mealMonth == calendar.get(Calendar.MONTH) + 1 && mealYear == calendar.get(Calendar.YEAR)) {
                    textCalendar.setText(getString(R.string.today_s_picks) + monthFormat.format(calendar.getTime()) + " " + dayOfMonth + ", " + year);

                } else {
                    textCalendar.setText(getString(R.string.your_meal_plan_for) + monthFormat.format(calendar.getTime()) + " " + dayOfMonth + ", " + year);
                }

            });

        } else {

            guestGroup.setVisibility(View.VISIBLE);
            textCalendar.setText(R.string.sign_in_now_to_scheduler_your_meals);


        }
        signInText.setOnClickListener((v) -> {

            Navigation.findNavController(requireView()).navigate(R.id.action_calenderFragment_to_loginFragment, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.calendarView, true)
                            .setPopUpTo(R.id.homeFragment, true)
                            .build());
        });
        continueAsAGuest.setOnClickListener((v) -> {

            guestGroup.setVisibility(View.GONE);


        });


    }

    private void loadMeals(int day, int month, int year) {
        if (mealDisposable != null && !mealDisposable.isDisposed()) {
            mealDisposable.dispose();
        }

        mealDisposable = presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(), day, month, year);
    }


    @Override
    public void onRemoveClickListener(CalenderMealModel calenderMealModel) {

        if (NetworkAvailability.isNetworkAvailable(requireContext())) {
            if (calenderMealModel.getDay() == mealDay && calenderMealModel.getMonth() == mealMonth && calenderMealModel.getYear() == mealYear) {
                Meal meal=new Meal();
                meal.setStrMeal(calenderMealModel.getStrMeal());
                meal.setStrInstructions(calenderMealModel.getStrInstructions());
                presenter.deleteMealFromCalendar(calenderMealModel);
                presenter.deleteCalendarMealFromFireStore(calenderMealModel);
                presenter.deleteMealFromMobileCalendar(calenderMealModel.getYear(), calenderMealModel.getMonth(), calenderMealModel.getDay(), meal);
                Snackbar snackbar = Snackbar
                        .make(requireView(), getString(R.string.meal_is_removed_from_calender), Snackbar.LENGTH_LONG).setActionTextColor(
                                getResources().getColor(R.color.primaryColor)
                        ).setTextColor(getResources().getColor(R.color.white))
                        .setAction(getString(R.string.undo), view -> {

                            presenter.addMealToCalendar(calenderMealModel);
                            presenter.insertCalendarMealToFireStore(calenderMealModel);
                            presenter.addMealToMobileCalendar(calenderMealModel.getYear(), calenderMealModel.getMonth(), calenderMealModel.getDay(), meal);

                        });
                snackbar.show();
            }
        } else {
            NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));
        }


    }


    @Override
    public void onItemClickListener(CalenderMealModel meal) {
        CalenderFragmentDirections.ActionCalenderFragmentToDetailsFragment action =
                CalenderFragmentDirections.actionCalenderFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);
    }


    @Override
    public void onGetCalendarListFromDatabase(List<CalenderMealModel> list) {
        myAdapter.setMealsList(list);
        myAdapter.notifyDataSetChanged();


    }


    @Override
    public void onSuccess(String message) {

        Log.i("TAG", "onSuccess: FIRESTORE " + message);

    }

    @Override
    public void onFailure(String errorMessage) {
        Log.i("TAG", "onFailure: FIRESTORE" + errorMessage);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PresenterImpl.compositeDisposable.clear();
    }
}