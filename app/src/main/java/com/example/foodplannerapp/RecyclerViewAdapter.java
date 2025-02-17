package com.example.foodplannerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.models.Meal;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";

    Context context;
    List<Meal> mealsList;

    public void setMealsList(List<Meal> mealsList) {
        this.mealsList = mealsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

       TextView mealName;
       TextView mealArea;
       ImageView mealImage;
       TextView mealCategory;
       ImageView mealCountryFlagIcon;
       View itemView;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           this.itemView=itemView;
           mealName=itemView.findViewById(R.id.mealName);
           mealArea=itemView.findViewById(R.id.mealArea);
           mealImage=itemView.findViewById(R.id.mealImg);
           mealCategory=itemView.findViewById(R.id.mealCategory);

       }
   }

   RecyclerViewAdapter(Context context , List<Meal>mealsList){

       this.context=context;
       this.mealsList=mealsList;

   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout,recyclerview,false);
        ViewHolder viewHolder=new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal=mealsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealArea.setText(meal.getStrArea());
        holder.mealCategory.setText(meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);

        Log.i(TAG, "onBindViewHolder: ");

    }



    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}
