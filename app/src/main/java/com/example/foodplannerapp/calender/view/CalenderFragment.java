package com.example.foodplannerapp.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.FireStoreRepositoryImpl;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.presenter.PresenterImpl;
import com.example.foodplannerapp.favorite.view.FavoriteFragmentDirections;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class CalenderFragment extends Fragment implements CalendarListener , ViewInterface{

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    LiveData<List<CalenderMealModel>> calendarMealsList;
    TextView textCalendar;
    int mealDay = 0;
    int mealMonth = 0;
    int mealYear = 0;
    Calendar calendar;


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
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), FireStoreRepositoryImpl.getInstance(FiresStoreServices.getInstance()),this);
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

        calendarMealsList = presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(), mealDay, mealMonth, mealYear);
        calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
            @Override
            public void onChanged(List<CalenderMealModel> calenderMealModels) {

                myAdapter.setMealsList(calenderMealModels);
                myAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(myAdapter);
                calendar.set(Calendar.MONTH, mealMonth - 1);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                String monthName = monthFormat.format(calendar.getTime());
                textCalendar.setText("Today's Picks: " + monthName + " " + mealDay + ", " + mealYear);


            }

        });


        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            mealDay = dayOfMonth;
            mealMonth = month + 1;
            mealYear = year;
            calendarMealsList.removeObservers(getViewLifecycleOwner());
            calendarMealsList = presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(), mealDay, mealMonth, mealYear);
            calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
                @Override
                public void onChanged(List<CalenderMealModel> calenderMealModels) {


                    myAdapter.setMealsList(calenderMealModels);
                    recyclerView.setAdapter(myAdapter);
                    calendar.set(Calendar.MONTH, month);
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                    String monthName = monthFormat.format(calendar.getTime());
                    if (mealDay == calendar.get(Calendar.DAY_OF_MONTH) && mealMonth == calendar.get(Calendar.MONTH) + 1 && mealYear == calendar.get(Calendar.YEAR)) {

                        textCalendar.setText("Today's Picks: " + monthName + " " + dayOfMonth + ", " + year);


                    } else {
                        textCalendar.setText("Your Meal Plan For " + monthName + " " + dayOfMonth + ", " + year);
                    }


                }
            });


        });


    }


    @Override
    public void onClickListener(CalenderMealModel meal) {

        if (meal.getDay() == mealDay && meal.getMonth() == mealMonth && meal.getYear() == mealYear) {
            presenter.deleteMealFromCalendar(meal);
            presenter.deleteCalendarMealFromFireStore(meal);


            Snackbar snackbar = Snackbar
                    .make(requireView(), "Meal is removed from calender", Snackbar.LENGTH_LONG).setActionTextColor(
                            getResources().getColor(R.color.primaryColor)
                    ).setTextColor(getResources().getColor(R.color.white))
                    .setAction("UNDO", view -> {
                        presenter.addMealToCalendar(meal);
                        presenter.insertCalendarMealToFireStore(meal);

                    });
            snackbar.show();
        }


    }

    @Override
    public void onItemClickListener(CalenderMealModel meal) {
        CalenderFragmentDirections.ActionCalenderFragmentToDetailsFragment action=
                CalenderFragmentDirections.actionCalenderFragmentToDetailsFragment(Integer.parseInt(meal.getIdMeal()));
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onSuccess(String message) {

        Log.i("TAG", "onSuccess: "+message);

    }

    @Override
    public void onFailure(String errorMessage) {
        Log.i("TAG", "onFailure: "+errorMessage);

    }
}