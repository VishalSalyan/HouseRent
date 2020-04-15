package com.example.houserent.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.data.HouseData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class WishListHouseAdapter extends RecyclerView.Adapter<WishListHouseAdapter.ViewHolder> {

    private ArrayList<HouseData> houseList;
    private Context context;

    // data is passed into the constructor
    public WishListHouseAdapter(Context context, ArrayList<HouseData> houseList) {

        this.context = context;
        this.houseList = houseList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_wishlist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final HouseData houseData = houseList.get(position);

        holder.name.setText(houseData.getAddress());
        holder.container.setOnClickListener(v -> {

        });
        Picasso.get().load(houseData.getHouseImage()).into(holder.carImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (houseList == null) {
            return 0;
        }
        return houseList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView container;
        private ImageView carImage;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_car_name);
            container = itemView.findViewById(R.id.cv_Wishlist);
            carImage = itemView.findViewById(R.id.iv_car_image);
        }

    }


}