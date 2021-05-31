package com.taylorngo.shoppinglist;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener {

    private ArrayList<ListItem> itemsList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private AddItemDialog addItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.shoppingList);

        itemsList = new ArrayList<>();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        setItemInfo();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(this, itemsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setItemInfo(){
        itemsList.add(new ListItem());
        itemsList.add(new ListItem());
        itemsList.add(new ListItem("Gorilla", "Animal", "Harambe", 10000, false));
    }

    private void addItem(){
        openAddItemDialog();
    }

    private void removeItem(){

    }

    public void openAddItemDialog(){
        addItemDialog = new AddItemDialog();
        addItemDialog.show(getSupportFragmentManager(), "Add Item Dialog");
    }

    @Override
    public void applyTexts(String name, String price, String desc, boolean purchased, String category) {
        if(price.trim().length() == 0){
            price = "0";
        }
        ListItem newItem = new ListItem();
        newItem.setName(name);
        newItem.setPrice(Double.parseDouble(price));
        newItem.setDescription(desc);
        newItem.setPurchased(purchased);
        newItem.setCategory(category);
        itemsList.add(newItem);
        adapter.notifyItemInserted(itemsList.size() - 1);
        addItemDialog.dismiss();
    }
}