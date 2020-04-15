package com.example.houserent.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.activity.HouseDetailActivity;
import com.example.houserent.data.HouseData;
import com.example.houserent.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HouseCollectionAdapter extends RecyclerView.Adapter<HouseCollectionAdapter.ViewHolder> {

    private ArrayList<HouseData> carList;
    private Context context;

    // data is passed into the constructor
    public HouseCollectionAdapter(Context context, ArrayList<HouseData> carList) {
        this.context = context;
        this.carList = carList;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_collection, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HouseData houseData = carList.get(position);

        holder.name.setText(houseData.getAddress());
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, HouseDetailActivity.class);
            intent.putExtra("houseId", houseData.getId());
            intent.putExtra("mode", Constants.VILLA_COLLECTION);
            context.startActivity(intent);
        });

        Picasso.get().load(houseData.getHouseImage()).into(holder.carImage);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (carList == null) {
            return 0;
        }
        return carList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView container;
        private ImageView carImage;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_car_name);
            container = itemView.findViewById(R.id.cv_collection1);
            carImage = itemView.findViewById(R.id.iv_car_image);
        }

    }


}