package com.example.foodplannerapp.details.view;

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
import com.example.foodplannerapp.data.model.Ingredient;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private final Context context;
    private List<Ingredient> ingredientList;

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

       ImageView ingredientImage;
       TextView ingredientName;
       TextView ingredientMeasure;
       View itemView;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           this.itemView=itemView;
           ingredientImage=itemView.findViewById(R.id.searchImg);
           ingredientName=itemView.findViewById(R.id.searchName);
           ingredientMeasure=itemView.findViewById(R.id.ingredientMeasure);


       }
   }

   RecyclerViewAdapter(Context context , List<Ingredient>ingredientList){

       this.context=context;
       this.ingredientList=ingredientList;

   }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item_layout,recyclerview,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient=ingredientList.get(position);
        holder.ingredientName.setText(ingredient.getIngredient());
        holder.ingredientMeasure.setText(ingredient.getMeasure());
        Glide.with(context).load(ingredient.getImage()).into(holder.ingredientImage);

    }



    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}
