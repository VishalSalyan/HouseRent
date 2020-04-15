package com.example.houserent.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houserent.R;
import com.example.houserent.data.FaqData;

import java.util.ArrayList;


public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    private ArrayList<FaqData> faqList;
    private Context context;

    // data is passed into the constructor
    public FaqAdapter(Context context, ArrayList<FaqData> faqList) {

        this.context = context;
        this.faqList = faqList;

    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_faq, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        FaqData faqData = faqList.get(position);

        holder.question.setText(faqData.getQuestion());
        holder.answer.setText(faqData.getAnswer());

        holder.cvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.answer.getVisibility() == View.GONE) {
                    holder.answer.setVisibility(View.VISIBLE);
                } else {
                    holder.answer.setVisibility(View.GONE);
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (faqList == null) {
            return 0;
        }
        return faqList.size();
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView answer;
        private CardView cvCollection;

        ViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            answer = itemView.findViewById(R.id.tv_answer);
            cvCollection = itemView.findViewById(R.id.cv_collection);
        }

    }


}