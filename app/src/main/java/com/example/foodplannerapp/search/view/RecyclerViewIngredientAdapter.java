package com.example.foodplannerapp.search.view;

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
import com.example.foodplannerapp.data.model.IngredientMeal;

import java.util.List;

public class RecyclerViewIngredientAdapter extends RecyclerView.Adapter<RecyclerViewIngredientAdapter.ViewHolder>{



    private final Context context;
    private List<IngredientMeal> ingredientList;
    private final SearchListener listener;



    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ingredientImage;
        TextView ingredientName;
        View itemView;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            ingredientImage =itemView.findViewById(R.id.searchImg);
            ingredientName =itemView.findViewById(R.id.searchName);


        }
    }

    public void setIngredientList(List<IngredientMeal> ingredientList) {
        this.ingredientList = ingredientList;
    }

    RecyclerViewIngredientAdapter(Context context , List<IngredientMeal> ingredientList, SearchListener listener){

        this.context=context;
        this.ingredientList =ingredientList;
        this.listener=listener;

    }


    @NonNull
    @Override
    public RecyclerViewIngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.search_item_layout,recyclerview,false);
        RecyclerViewIngredientAdapter.ViewHolder viewHolder=new RecyclerViewIngredientAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewIngredientAdapter.ViewHolder holder, int position) {
        IngredientMeal ingredient= ingredientList.get(position);
        holder.ingredientName.setText(ingredient.getStrIngredient());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png").into(holder.ingredientImage);
        holder.ingredientImage.setOnClickListener((v)->{

            listener.onClickListener(ingredientList.get(position));
        });


    }



    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
