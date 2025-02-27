package com.example.foodplannerapp.favorite.view;

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
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.utilis.CountryCodeMapper;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private final Context context;
    private List<FavoriteMealModel> mealsList;
    private final FavoriteListener listener;

    public void setMealsList(List<FavoriteMealModel> mealsList) {
        this.mealsList = mealsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;
        TextView mealArea;
        ImageView mealImage;
        TextView mealCategory;
        ImageView mealCountryFlagIcon;
        ImageView favoriteIcon;
        View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mealName = itemView.findViewById(R.id.searchName);
            mealArea = itemView.findViewById(R.id.mealArea);
            mealImage = itemView.findViewById(R.id.searchImg);
            mealCategory = itemView.findViewById(R.id.ingredientMeasure);
            mealCountryFlagIcon = itemView.findViewById(R.id.mealCountryFlagIcon);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);

        }
    }

    public RecyclerViewAdapter(Context context, List<FavoriteMealModel> mealsList, FavoriteListener listener) {

        this.context = context;
        this.mealsList = mealsList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.fav_item_layout, recyclerview, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteMealModel meal = mealsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealArea.setText(meal.getStrArea());
        holder.mealCategory.setText(meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(meal.getStrArea(), "unknown");
        Glide.with(context).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(holder.mealCountryFlagIcon);
        holder.favoriteIcon.setOnClickListener((v) -> {
            listener.onRemoveClickListener(mealsList.get(position));

        });
        holder.mealImage.setOnClickListener((v) -> {

            listener.onItemClickListener(mealsList.get(position));
        });

    }


    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}
