package com.example.houserent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.adapters.ExploreHouseAdapter;
import com.example.houserent.adapters.NewHouseAdapter;
import com.example.houserent.data.HouseData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;

import java.util.ArrayList;

import static com.example.houserent.utils.Toasts.show;


public class HomeFragment extends Fragment {
    private TextView tvExploreCar, tvNewCarCollection;
    private RecyclerView mRecyclerView;
    private RecyclerView newCarsRecyclerView;

    private ExploreHouseAdapter exploreHouseAdapter;
    private NewHouseAdapter newHouseAdapter;

    private ArrayList<HouseData> houseList = new ArrayList<>();
    private ArrayList<HouseData> newHouseList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.explore_recyclerView);
        newCarsRecyclerView = view.findViewById(R.id.new_cars_recycler_view);
        tvExploreCar = view.findViewById(R.id.tv_explore_car);
        tvNewCarCollection = view.findViewById(R.id.tv_new_car_collection);

        initializeExploreAdapter();
        initializeNewCarsAdapter();
        fetchExploreCar();
        fetchNewCar();

        return view;
    }

    private void fetchNewCar() {
        FireBaseRepo.I.fetchNewCar(new ServerResponse<ArrayList<HouseData>>() {
            @Override
            public void onSuccess(ArrayList<HouseData> body) {
                if (body.size() == 0) {
                    tvNewCarCollection.setVisibility(View.GONE);
                }
                newHouseList.clear();
                newHouseList.addAll(body);
                newHouseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void fetchExploreCar() {
        FireBaseRepo.I.fetchExploreCar(new ServerResponse<ArrayList<HouseData>>() {
            @Override
            public void onSuccess(ArrayList<HouseData> body) {
                if (body.size() == 0) {
                    tvExploreCar.setVisibility(View.GONE);
                }
                houseList.clear();
                houseList.addAll(body);
                exploreHouseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.toString());
            }
        });
    }

    private void initializeNewCarsAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        newCarsRecyclerView.setLayoutManager(gridLayoutManager);

        // Initialize the adapter and attach it to the RecyclerView
        newHouseAdapter = new NewHouseAdapter(getActivity(), newHouseList);
        newCarsRecyclerView.setAdapter(newHouseAdapter);
    }

    private void initializeExploreAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        exploreHouseAdapter = new ExploreHouseAdapter(getActivity(), houseList);
        mRecyclerView.setAdapter(exploreHouseAdapter);
    }

}


