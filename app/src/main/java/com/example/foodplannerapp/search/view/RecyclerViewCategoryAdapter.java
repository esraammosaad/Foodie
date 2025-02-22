package com.example.foodplannerapp.search.view;

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
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.models.Category;

import java.util.List;

public class RecyclerViewCategoryAdapter extends RecyclerView.Adapter<RecyclerViewCategoryAdapter.ViewHolder> {

    private static final String TAG = "MyAdapter";

    Context context;
    List<Category> categoryList;
    SearchListener listener;



    static class ViewHolder extends RecyclerView.ViewHolder{

       ImageView categoryImage;
       TextView categoryName;
       View itemView;




       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           this.itemView=itemView;
           categoryImage =itemView.findViewById(R.id.searchImg);
           categoryName =itemView.findViewById(R.id.searchName);


       }
   }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    RecyclerViewCategoryAdapter(Context context , List<Category> categoryList, SearchListener listener){

       this.context=context;
       this.categoryList=categoryList;
       this.listener=listener;

   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.search_item_layout,recyclerview,false);
        ViewHolder viewHolder=new ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category=categoryList.get(position);
        holder.categoryName.setText(category.getStrCategory());
        Glide.with(context).load(category.getStrCategoryThumb()).into(holder.categoryImage);
        holder.categoryImage.setOnClickListener((v)->{

            listener.onClickListener(categoryList.get(position));
        });


        Log.i(TAG, "onBindViewHolder: ");

    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
