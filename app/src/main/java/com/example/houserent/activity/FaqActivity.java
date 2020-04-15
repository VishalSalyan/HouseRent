package com.example.houserent.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.adapters.FaqAdapter;
import com.example.houserent.data.FaqData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;

import java.util.ArrayList;

public class FaqActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<FaqData> faqList = new ArrayList<>();
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        recyclerView = findViewById(R.id.recycler_view);
        initializeAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchFaqData();
    }

    private void fetchFaqData() {
        FireBaseRepo.I.fetchFaq(new ServerResponse<ArrayList<FaqData>>() {
            @Override
            public void onSuccess(ArrayList<FaqData> body) {
                faqList.clear();
                faqList.addAll(body);
                faqAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void initializeAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FaqActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        faqAdapter = new FaqAdapter(FaqActivity.this, faqList);
        recyclerView.setAdapter(faqAdapter);
    }
}
