package com.example.foodplannerapp.search_details.view;

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
import com.example.foodplannerapp.data.models.GetMealsByFilterResponse;
import com.example.foodplannerapp.data.models.Meal;
import com.example.foodplannerapp.data.models.MealByFilter;
import com.example.foodplannerapp.home.view.HomeListener;
import com.example.foodplannerapp.utilis.CountryCodeMapper;

import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    Context context;
    List<MealByFilter> mealsList;
    SearchDetailsListener listener;

    public void setMealsList(List<MealByFilter> mealsList) {
        this.mealsList = mealsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

       TextView mealName;
       ImageView mealImage;
       View itemView;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           this.itemView=itemView;
           mealName=itemView.findViewById(R.id.searchName);
           mealImage=itemView.findViewById(R.id.searchImg);

       }
   }

   public RecyclerViewAdapter(Context context, List<MealByFilter> mealsList, SearchDetailsListener listener){

       this.context=context;
       this.mealsList=mealsList;
       this.listener=listener;

   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.search_details_item_layout,recyclerview,false);
        ViewHolder viewHolder=new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealByFilter meal=mealsList.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        holder.mealImage.setOnClickListener((v)->{

            listener.onClickListener(mealsList.get(position));
        });

    }



    @Override
    public int getItemCount() {
        return mealsList.size();
    }
}
