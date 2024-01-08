package com.example.logd;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderFAQ extends RecyclerView.ViewHolder {

    TextView question, answer;

    public ViewHolderFAQ(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.question);
        answer = itemView.findViewById(R.id.answer);


    }
}