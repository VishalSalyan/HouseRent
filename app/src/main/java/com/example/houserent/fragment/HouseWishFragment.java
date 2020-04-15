package com.example.houserent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.adapters.WishListHouseAdapter;
import com.example.houserent.data.FavouriteHouseData;
import com.example.houserent.data.HouseData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;
import com.example.houserent.utils.SessionData;

import java.util.ArrayList;

import static com.example.houserent.utils.Toasts.show;


public class HouseWishFragment extends Fragment {

    private RecyclerView recyclerView;
    private WishListHouseAdapter wishListHouseAdapter;
    private ArrayList<HouseData> houseList = new ArrayList<>();

    public HouseWishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_collection, container, false);
        recyclerView = view.findViewById(R.id.collection_recycler_view);
        initializeAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FireBaseRepo.I.wishListHouses(SessionData.getInstance().getLocalData().getEmail(), new ServerResponse<ArrayList<FavouriteHouseData>>() {
            @Override
            public void onSuccess(ArrayList<FavouriteHouseData> body) {
                houseList.clear();
                for (FavouriteHouseData item : body) {
                    for (int i = 0; i < SessionData.getInstance().totalCarList.size(); i++) {
                        if (item.getHouseId().equals(SessionData.getInstance().totalCarList.get(i).getId())) {
                            houseList.add(SessionData.getInstance().totalCarList.get(i));
                            break;
                        }
                    }
                }
                wishListHouseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                show.longMsg(getActivity(), error.getMessage());
            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        wishListHouseAdapter = new WishListHouseAdapter(getActivity(), houseList);
        recyclerView.setAdapter(wishListHouseAdapter);
    }

}

