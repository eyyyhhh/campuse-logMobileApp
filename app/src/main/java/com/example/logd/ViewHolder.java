package com.example.logd;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView date, time, schoolID, remarks;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.date);
        time = itemView.findViewById(R.id.time);
        schoolID = itemView.findViewById(R.id.schoolID);
        remarks = itemView.findViewById(R.id.remarks);

    }
}