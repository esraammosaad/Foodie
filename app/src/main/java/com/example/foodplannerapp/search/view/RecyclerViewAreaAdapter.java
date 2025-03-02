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
import com.example.foodplannerapp.data.model.Area;
import com.example.foodplannerapp.utilis.CountryCodeMapper;

import java.util.List;
import java.util.Map;

public class RecyclerViewAreaAdapter extends RecyclerView.Adapter<RecyclerViewAreaAdapter.ViewHolder>{

    private final Context context;
    private List<Area> areaList;
    private final SearchListener listener;



    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView areaImage;
        TextView areaName;
        View itemView;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            areaImage =itemView.findViewById(R.id.searchImg);
            areaName=itemView.findViewById(R.id.searchName);


        }
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    RecyclerViewAreaAdapter(Context context , List<Area> areaList, SearchListener listener){

        this.context=context;
        this.areaList=areaList;
        this.listener=listener;

    }


    @NonNull
    @Override
    public RecyclerViewAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(recyclerview.getContext());
        View view = layoutInflater.inflate(R.layout.search_item_layout,recyclerview,false);
        RecyclerViewAreaAdapter.ViewHolder viewHolder=new RecyclerViewAreaAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAreaAdapter.ViewHolder holder, int position) {
        Area area=areaList.get(position);
        holder.areaName.setText(area.getStrArea());
        Map<String, String> countryCodeMap = CountryCodeMapper.getCountryCodeMap();
        String countryCode = countryCodeMap.getOrDefault(area.getStrArea(), "unknown");
        Glide.with(context).load("https://flagsapi.com/" + countryCode.toUpperCase() + "/flat/64.png").into(holder.areaImage);
        holder.areaImage.setOnClickListener((v)->{

            listener.onClickListener(areaList.get(position));
        });


    }



    @Override
    public int getItemCount() {
        return areaList.size();
    }
}
