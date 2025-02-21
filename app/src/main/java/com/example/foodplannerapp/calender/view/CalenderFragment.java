package com.example.foodplannerapp.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.home.view.HomeListener;
import com.example.foodplannerapp.home.view.RecyclerViewAdapter;

import java.util.Arrays;
import java.util.List;


public class CalenderFragment extends Fragment implements HomeListener{

    CalendarView calendarView;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;




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
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter=new RecyclerViewAdapter(getContext(), List.of(),this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onClickListener(Meal meal) {

    }
}