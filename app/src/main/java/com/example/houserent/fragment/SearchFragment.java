package com.example.houserent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.adapters.SearchAdapter;
import com.example.houserent.data.CarData;
import com.example.houserent.utils.SessionData;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        MaterialSearchView searchView = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        initializeAdapter();
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                filter(newText);
                return true;
            }
        });
        return view;
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter = new SearchAdapter(getActivity(), SessionData.getInstance().totalCarList);
        recyclerView.setAdapter(searchAdapter);
    }

    public void notifySearchList() {
        searchAdapter.notifyDataSetChanged();
    }

    private void filter(String text) {
        ArrayList<CarData> filteredList = new ArrayList<>();

        for (CarData item : SessionData.getInstance().totalCarList) {
            if (item.getCarName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        searchAdapter.filterList(filteredList);
    }
}