package com.taylorngo.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<ListItem> itemsList;
    Context context;

    public RecyclerAdapter(Context ct, ArrayList<ListItem> itemsList){
        this.itemsList = itemsList;
        this.context = ct;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private TextView priceTxt;
        private TextView descriptionTxt;
        private ImageView itemImage;
        private CheckBox purchasedBox;
        private ToggleButton toggleDetails;
        private ConstraintLayout mainLayout;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.nameText);
            priceTxt = view.findViewById(R.id.priceText);
            descriptionTxt = view.findViewById(R.id.descriptionText);
            itemImage = view.findViewById(R.id.itemImage);
            purchasedBox = view.findViewById(R.id.purchasedBox);
            toggleDetails = view.findViewById(R.id.detailsBtn);
            mainLayout = view.findViewById(R.id.list_item_row);
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ListItem item = itemsList.get(position);

        String name = item.getName();
        holder.nameTxt.setText(name);

        String price = "$" + item.getPrice();
        holder.priceTxt.setText(price);

        String description = item.getDescription();
        holder.descriptionTxt.setText(description);
        holder.descriptionTxt.setVisibility(View.INVISIBLE);

        String category = item.getCategory();
        if(category.equals("Food")){
            holder.itemImage.setImageResource(R.drawable.food);
        }
        else if(category.equals("Animal")){
            holder.itemImage.setImageResource(R.drawable.animal);
        }
        else if(category.equals("Clothing")){
            holder.itemImage.setImageResource(R.drawable.clothing);
        }
        else{
            holder.itemImage.setImageResource(R.drawable.technology);
        }

        holder.purchasedBox.setChecked(item.isPurchased());

        holder.toggleDetails.setTextOff("Show details");
        holder.toggleDetails.setTextOn("Hide details");
        holder.toggleDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.toggleDetails.isChecked()){
                    holder.descriptionTxt.setVisibility(View.VISIBLE);
                }
                else{
                    holder.descriptionTxt.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                ListItem temp = itemsList.get(position);
                intent.putExtra("data1", temp.getName());
                intent.putExtra("data2", temp.getDescription());
                intent.putExtra("data3", "$" + temp.getPrice());
                intent.putExtra("data4", temp.getCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
