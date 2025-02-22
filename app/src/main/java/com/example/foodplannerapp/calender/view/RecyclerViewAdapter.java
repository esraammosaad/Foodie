package com.example.foodplannerapp.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.home.view.HomeListener;
import com.example.foodplannerapp.utilis.CountryCodeMapper;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    Context context;
    List<CalenderMealModel> mealsList;
    CalendarListener listener;

    public void setMealsList(List<CalenderMealModel> mealsList) {
        this.mealsList = mealsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        TextView mealArea;
        ImageView mealImage;
        TextView mealCategory;
        TextView instructions;
        ImageView removeIcon;
        ImageView mealCountryFlagIcon;
        View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mealName = itemView.findViewById(R.id.ingredientName);
            mealArea = itemView.findViewById(R.id.mealArea);
            mealImage = itemView.findViewById(R.id.ingredientImg);
            mealCategory = itemView.findViewById(R.id.ingredientMeasure);
            instructions = itemView.findViewById(R.id.instructionsText);
            mealCountryFlagIcon = itemView.findViewById(R.id.mealCountryFlagIcon);
            removeIcon=itemView.findViewById(R.id.removeIcon);

        }
    }

    public RecyclerViewAdapter(Context context, List<CalenderMealModel> mealsList, CalendarListener listener) {

        this.context = context;
        this.mealsList = mealsList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_item_layout, recyclerview, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalenderMealModel meal = mealsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealArea.setText(meal.getStrArea());
        holder.mealCategory.setText(meal.getStrCategory());
        holder.instructions.setText(meal.getStrInstructions());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(meal.getStrArea(), "unknown");
        Glide.with(context).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(holder.mealCountryFlagIcon);
        holder.removeIcon.setOnClickListener((v) -> {
            listener.onClickListener(mealsList.get(position));

        });

    }


    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}
