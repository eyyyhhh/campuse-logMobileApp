package com.example.logd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterFAQ extends RecyclerView.Adapter<ViewHolderFAQ> {

    Context context;
    List<ListDataFAQ> item;

    public AdapterFAQ(Context context, List<ListDataFAQ> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public ViewHolderFAQ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderFAQ(LayoutInflater.from(context).inflate(R.layout.list_itemfaq,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFAQ holder, int position) {
        holder.question.setText(item.get(position).getQuestion());
        holder.answer.setText(item.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
