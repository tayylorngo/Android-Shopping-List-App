package com.taylorngo.shoppinglist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
    private Context context;
    private Cursor mCursor;
    private EditItemDialog editItemDialog;

    public RecyclerAdapter(Context ct, Cursor cursor){
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
        private FloatingActionButton deleteItemBtn;
        private Button editButton;
        private ConstraintLayout mainLayout;

        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.nameText);
            priceTxt = view.findViewById(R.id.priceText);
            descriptionTxt = view.findViewById(R.id.descriptionText);
            itemImage = view.findViewById(R.id.itemImage);
            purchasedBox = view.findViewById(R.id.purchasedBox);
            toggleDetails = view.findViewById(R.id.detailsBtn);
            deleteItemBtn = view.findViewById(R.id.deleteItemButton);
            editButton = view.findViewById(R.id.editBtn);
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

        ListItem tempItem = new ListItem(name, category, description, price, Boolean.parseBoolean(purchased));

        long id = mCursor.getLong(mCursor.getColumnIndex(ListDatabase.ListItemEntry._ID));
        holder.itemView.setTag(id);

        holder.nameTxt.setText(name);

        String price2 = "$" + price;
        holder.priceTxt.setText(price2);

        holder.descriptionTxt.setText(description);
        holder.descriptionTxt.setVisibility(View.INVISIBLE);

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
        holder.purchasedBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase mDatabase = MainActivity.getMyDatabase();
                boolean checked = holder.purchasedBox.isChecked();
                ContentValues cv = new ContentValues();
                cv.put(ListDatabase.ListItemEntry.COLUMN_NAME, name);
                cv.put(ListDatabase.ListItemEntry.COLUMN_CATEGORY, category);
                cv.put(ListDatabase.ListItemEntry.COLUMN_PRICE, price);
                cv.put(ListDatabase.ListItemEntry.COLUMN_DESCRIPTION, description);
                if(checked){
                    cv.put(ListDatabase.ListItemEntry.COLUMN_PURCHASED, String.valueOf(true));
                }
                else{
                    cv.put(ListDatabase.ListItemEntry.COLUMN_PURCHASED, String.valueOf(false));
                }
                mDatabase.update(ListDatabase.ListItemEntry.TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(id)});
                MainActivity.getAdapter().swapCursor(mDatabase.query(ListDatabase.ListItemEntry.TABLE_NAME,
                        null, null, null, null, null,
                        ListDatabase.ListItemEntry.COLUMN_TIMESTAMP +  " ASC"
                ));
            }
        });

        holder.toggleDetails.setTextOff("Show details");
        holder.toggleDetails.setTextOn("Hide details");
        if(holder.toggleDetails.isChecked()){
            holder.descriptionTxt.setVisibility(View.VISIBLE);
        }
        else{
            holder.descriptionTxt.setVisibility(View.INVISIBLE);
        }
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

        holder.deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(id);
            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("data1", name);
                intent.putExtra("data2", description);
                intent.putExtra("data3", price2);
                intent.putExtra("data4", category);
                intent.putExtra("data5", purchased);
                context.startActivity(intent);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditDialog(id, tempItem);
            }
        });
    }

    public void openEditDialog(long id, ListItem item){
        editItemDialog = new EditItemDialog(id, item);
        editItemDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "Edit Item Dialog");
    }

    public void deleteItem(long id){
        SQLiteDatabase mDatabase = MainActivity.getMyDatabase();
        mDatabase.delete(ListDatabase.ListItemEntry.TABLE_NAME,
                ListDatabase.ListItemEntry._ID + "=" + id, null);
        MainActivity.getAdapter().swapCursor(mDatabase.query(ListDatabase.ListItemEntry.TABLE_NAME,
                null, null, null, null, null,
                ListDatabase.ListItemEntry.COLUMN_TIMESTAMP +  " ASC"
        ));
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

    public EditItemDialog getEditItemDialog(){
        return editItemDialog;
    }
}
