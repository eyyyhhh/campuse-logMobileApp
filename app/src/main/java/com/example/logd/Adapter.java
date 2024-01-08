package com.example.logd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<ListData> item;

    public Adapter(Context context, List<ListData> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(item.get(position).getDate());
        holder.time.setText(item.get(position).getTime());
        holder.remarks.setText(item.get(position).getRemarks());
        holder.schoolID.setText(item.get(position).getSchoolID());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
