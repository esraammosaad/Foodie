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
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.calender.presenter.PresenterImpl;
import com.example.foodplannerapp.home.view.HomeListener;
import com.example.foodplannerapp.calender.view.RecyclerViewAdapter;

import java.util.Calendar;
import java.util.List;


public class CalenderFragment extends Fragment implements HomeListener{

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerViewAdapter myAdapter;
    PresenterImpl presenter;
    LiveData<List<CalenderMealModel>> calendarMealsList;
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
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter=new RecyclerViewAdapter(getContext(), List.of(),this);
        recyclerView.setAdapter(myAdapter);
        calendar=Calendar.getInstance();
        calendarMealsList=presenter.getAllMealsFromCalendar(UserAuthentication.getInstance().getCurrentUser().getUid(), calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
            @Override
            public void onChanged(List<CalenderMealModel> calenderMealModels) {

                myAdapter.setMealsList(calenderMealModels);
                myAdapter.notifyDataSetChanged();


            }
        });
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendarMealsList=presenter.getAllMealsFromCalendar(UserAuthentication.getInstance().getCurrentUser().getUid(),dayOfMonth,month,year);
            calendarMealsList.observe(getViewLifecycleOwner(), new Observer<List<CalenderMealModel>>() {
                @Override
                public void onChanged(List<CalenderMealModel> calenderMealModels) {

                    myAdapter.setMealsList(calenderMealModels);
                    myAdapter.notifyDataSetChanged();


                }
            });


        });


    }

    @Override
    public void onClickListener(Meal meal) {

    }
}