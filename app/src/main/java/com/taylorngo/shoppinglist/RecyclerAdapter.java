package com.taylorngo.shoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<ListItem> itemsList;

    public RecyclerAdapter(ArrayList<ListItem> itemsList){
        this.itemsList = itemsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView categoryTxt;
        private TextView priceTxt;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.nameText);
            categoryTxt = view.findViewById(R.id.categoryText);
            priceTxt = view.findViewById(R.id.priceText);
        }
    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        ListItem item = itemsList.get(position);
        String name = item.getName();
        holder.nameTxt.setText(name);
        String category = item.getCategory();
        holder.categoryTxt.setText(category);
        double price = item.getPrice();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
