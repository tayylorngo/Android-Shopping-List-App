package com.taylorngo.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    private Context context;
    private Cursor mCursor;

    public RecyclerAdapter(Context ct, Cursor cursor){
        this.itemsList = itemsList;
        this.mCursor = cursor;
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
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ListDatabase.ListItemEntry.COLUMN_NAME));
        double price = mCursor.getDouble(mCursor.getColumnIndex(ListDatabase.ListItemEntry.COLUMN_PRICE));
        String description = mCursor.getString(mCursor.getColumnIndex(ListDatabase.ListItemEntry.COLUMN_DESCRIPTION));
        String category = mCursor.getString(mCursor.getColumnIndex(ListDatabase.ListItemEntry.COLUMN_CATEGORY));
        String purchased = mCursor.getString(mCursor.getColumnIndex(ListDatabase.ListItemEntry.COLUMN_PURCHASED));

//        ListItem item = itemsList.get(position);

//        String name = item.getName();
        holder.nameTxt.setText(name);

        String price2 = "$" + price;
        holder.priceTxt.setText(price2);

//        String description = item.getDescription();
        holder.descriptionTxt.setText(description);
        holder.descriptionTxt.setVisibility(View.INVISIBLE);

//        String category = item.getCategory();
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

        holder.purchasedBox.setChecked(Boolean.parseBoolean(purchased));

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
//                ListItem temp = itemsList.get(position);
                intent.putExtra("data1", name);
                intent.putExtra("data2", description);
                intent.putExtra("data3", price2);
                intent.putExtra("data4", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
