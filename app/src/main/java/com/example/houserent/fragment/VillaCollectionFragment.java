package com.example.houserent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.adapters.HouseCollectionAdapter;
import com.example.houserent.data.HouseData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;

import java.util.ArrayList;


public class VillaCollectionFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HouseCollectionAdapter collectionAdapter;
    private ArrayList<HouseData> houseList = new ArrayList<>();

    public VillaCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mRecyclerView = view.findViewById(R.id.collection_recycler_view);
        initializeAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchCarList();
    }

    private void fetchCarList() {
        FireBaseRepo.I.fetchCollection(new ServerResponse<ArrayList<HouseData>>() {
            @Override
            public void onSuccess(ArrayList<HouseData> body) {
                houseList.clear();
                houseList.addAll(body);
                collectionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        collectionAdapter = new HouseCollectionAdapter(getActivity(), houseList);
        mRecyclerView.setAdapter(collectionAdapter);
    }

}

