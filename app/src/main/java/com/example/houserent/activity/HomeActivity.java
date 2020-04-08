package com.example.houserent.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.houserent.R;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;
import com.example.houserent.fragment.CarWishFragment;
import com.example.houserent.fragment.CollectionFragment;
import com.example.houserent.fragment.HomeFragment;
import com.example.houserent.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.houserent.utils.Toasts.show;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    HomeFragment homeFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        HomeFragment homeFragment = new HomeFragment();
        openFragment(homeFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBaseRepo.I.searchCar(new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.notifySearchList();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(HomeActivity.this, error.getMessage());
            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_home:
                            show.shortMsg(HomeActivity.this, "Home");
                            homeFragment = new HomeFragment();
                            openFragment(homeFragment);
                            break;
                        case R.id.bottom_category:
                            CollectionFragment collectionFragment = new CollectionFragment();
                            openFragment(collectionFragment);
                            show.shortMsg(HomeActivity.this, "Collection");
                            break;
                        case R.id.bottom_favorites:
                            CarWishFragment carWishFragment = new CarWishFragment();
                            openFragment(carWishFragment);
                            show.shortMsg(HomeActivity.this, "WishList");
                            break;
                        case R.id.bottom_search:
                            SearchFragment searchFragment = new SearchFragment();
                            openFragment(searchFragment);
                            show.shortMsg(HomeActivity.this, "Search");
                            break;
                    }
                    return true;
                }
            };

    public void openFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}