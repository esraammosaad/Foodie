package com.example.foodplannerapp.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.presenter.PresenterImpl;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;


public class CalenderFragment extends Fragment implements CalendarListener{

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    LiveData<List<CalenderMealModel>> calendarMealsList;
    int mealDay=0;
    int mealMonth=0;
    int mealYear=0;
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
        calendarView=view.findViewById(R.id.calendarView);
        recyclerView=view.findViewById(R.id.calendarRecyclerView);
        presenter = new PresenterImpl(MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter=new RecyclerViewAdapter(getContext(), List.of(),this);
        recyclerView.setAdapter(myAdapter);
        calendar=Calendar.getInstance();
        mealDay=calendar.get(Calendar.DAY_OF_MONTH);
        mealMonth=calendar.get(Calendar.MONTH)+1;
        mealYear=calendar.get(Calendar.YEAR);

            calendarMealsList=presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(), mealDay,mealMonth,mealYear);
            calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
                @Override
                public void onChanged(List<CalenderMealModel> calenderMealModels) {

                    myAdapter.setMealsList(calenderMealModels);
                    myAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(myAdapter);



                }

            });





        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            mealDay=dayOfMonth;
            mealMonth=month+1;
            mealYear=year;
            calendarMealsList.removeObservers(getViewLifecycleOwner());
            calendarMealsList=presenter.getAllMealsFromCalendar(presenter.getCurrentUser().getUid(),mealDay,mealMonth,mealYear);
            calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
                @Override
                public void onChanged(List<CalenderMealModel> calenderMealModels) {

                    myAdapter.setMealsList(calenderMealModels);
                    recyclerView.setAdapter(myAdapter);



                }
            });


        });


    }


    @Override
    public void onClickListener(CalenderMealModel meal) {

        if(meal.getDay()==mealDay && meal.getMonth()==mealMonth&& meal.getYear()==mealYear){
            presenter.deleteMealFromCalendar(meal);


            Snackbar snackbar = Snackbar
                    .make(requireView(), "Meal is removed from calender", Snackbar.LENGTH_LONG).setActionTextColor(
                            getResources().getColor(R.color.primaryColor)
                    ).setTextColor(getResources().getColor(R.color.white))
                    .setAction("UNDO", view -> {
                        presenter.addMealToCalendar(meal);

                    });
            snackbar.show();
        }





    }
}