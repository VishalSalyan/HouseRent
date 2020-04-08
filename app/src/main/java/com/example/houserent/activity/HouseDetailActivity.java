package com.example.houserent.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.houserent.R;
import com.example.houserent.data.CarData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;
import com.squareup.picasso.Picasso;

import static com.example.houserent.utils.Toasts.show;

public class HouseDetailActivity extends AppCompatActivity {

    private TextView tvCarName, tvDescription, tvTransmission, tvMileage, tvColor, tvBodyType, tvYear,
            tvMake, tvFuelType, tvModel, tvOwnerName, tvOwnerPhoneNumber, tvOwnerEmail, tvOwnerAddress;
    private ImageView carImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBaseRepo.I.getCarDetails("id", "explore_car", new ServerResponse<CarData>() {
            @Override
            public void onSuccess(CarData body) {
                setData(body);
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(HouseDetailActivity.this, error.getMessage());
            }
        });
    }

    private void setData(CarData carData) {
        Picasso.get().load(carData.getCarImage()).into(carImage);
        tvCarName.setText(carData.getCarName());
        tvDescription.setText(carData.getDescription());
        tvTransmission.setText(carData.getTransmission());
        tvMileage.setText(carData.getMileage());
        tvColor.setText(carData.getColor());
        tvBodyType.setText(carData.getBodyType());
        tvYear.setText(carData.getYear());
        tvMake.setText(carData.getMake());
        tvFuelType.setText(carData.getFuelType());
        tvModel.setText(carData.getModel());
        tvOwnerName.setText(carData.getOwnerName());
        tvOwnerPhoneNumber.setText(carData.getOwnerPhoneNumber());
        tvOwnerEmail.setText(carData.getOwnerEmail());
        tvOwnerAddress.setText(carData.getOwnerAddress());
    }

    private void initViews() {
        carImage = findViewById(R.id.iv_car_image);
        tvCarName = findViewById(R.id.tv_car_name);
        tvDescription = findViewById(R.id.tv_description);
        tvTransmission = findViewById(R.id.tv_transmission);
        tvMileage = findViewById(R.id.tv_mileage);
        tvColor = findViewById(R.id.tv_color);
        tvBodyType = findViewById(R.id.tv_body_type);
        tvYear = findViewById(R.id.tv_year);
        tvMake = findViewById(R.id.tv_make);
        tvFuelType = findViewById(R.id.tv_fuel_type);
        tvModel = findViewById(R.id.tv_model);
        tvOwnerName = findViewById(R.id.tv_owner_name);
        tvOwnerPhoneNumber = findViewById(R.id.tv_owner_phone_number);
        tvOwnerEmail = findViewById(R.id.tv_owner_email);
        tvOwnerAddress = findViewById(R.id.tv_owner_address);
    }
}
