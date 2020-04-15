package com.example.houserent.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.houserent.R;
import com.example.houserent.data.HouseData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;
import com.example.houserent.utils.SessionData;
import com.squareup.picasso.Picasso;

import static com.example.houserent.utils.Toasts.show;


public class HouseDetailActivity extends AppCompatActivity {

    private TextView tvCarName, tvDescription, tvTransmission, tvMileage, tvColor, tvBodyType, tvYear,
            tvMake, tvFuelType, tvModel, tvOwnerName, tvOwnerPhoneNumber, tvOwnerEmail, tvOwnerAddress;
    private ImageView houseImage;
    private Button addToWishList;

    private String id;
    private String mode;
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("House Details");

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        id = bundle.getString("houseId");
        mode = bundle.getString("mode");
        checkWishList();
        fetchCarDetails();
    }

    private void setData(HouseData houseData) {
        Picasso.get().load(houseData.getHouseImage()).into(houseImage);
        tvCarName.setText(houseData.getAddress());
        tvDescription.setText(houseData.getBedrooms());
        tvTransmission.setText(houseData.getLandMarks());
        tvMileage.setText(houseData.getOverlooking());
        tvColor.setText(houseData.getRentalValue());
        tvBodyType.setText(houseData.getSecurityDeposit());
        tvYear.setText(houseData.getColor());
        tvMake.setText(houseData.getDescription());
        tvFuelType.setText(houseData.getHouseImage());
        tvModel.setText(houseData.getHouseName());
        tvOwnerName.setText(houseData.getOwnerName());
        tvOwnerPhoneNumber.setText(houseData.getOwnerPhoneNumber());
        tvOwnerEmail.setText(houseData.getOwnerEmail());
        tvOwnerAddress.setText(houseData.getOwnerAddress());
    }

    private void initViews() {
        addToWishList = findViewById(R.id.btn_add_to_wish_list);
        houseImage = findViewById(R.id.iv_car_image);
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

        addToWishList.setOnClickListener(v -> FireBaseRepo.I.setWishListCars(isDeleteMode, SessionData.getInstance().getLocalData().getEmail(), id, mode, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                if (isDeleteMode) {
                    isDeleteMode = false;
                    addToWishList.setText(getResources().getString(R.string.add_to_wishlist));
                } else {
                    show.longMsg(HouseDetailActivity.this, "Added successfully to WishList");
                }
                checkWishList();
            }

            @Override
            public void onFailure(Throwable error) {

            }
        }));
    }

    private void checkWishList() {
        if (SessionData.getInstance().getLocalData() != null)
            if (SessionData.getInstance().getLocalData().getFavouriteHouse() != null || SessionData.getInstance().getLocalData().getFavouriteHouse().size() != 0) {
                for (int i = 0; i < SessionData.getInstance().getLocalData().getFavouriteHouse().size(); i++) {
                    if (id.equals(SessionData.getInstance().getLocalData().getFavouriteHouse().get(i).getHouseId())) {
                        isDeleteMode = true;
                        addToWishList.setText(getResources().getString(R.string.remove_from_wish_list));
                    }
                }
            }
    }

    private void fetchCarDetails() {
        FireBaseRepo.I.getCarDetails(id, mode, new ServerResponse<HouseData>() {
            @Override
            public void onSuccess(HouseData body) {
                setData(body);
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(HouseDetailActivity.this, error.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}
