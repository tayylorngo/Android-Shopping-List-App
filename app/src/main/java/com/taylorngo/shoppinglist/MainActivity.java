package com.taylorngo.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ListItem> itemsList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.shoppingList);

        itemsList = new ArrayList<>();

        setItemInfo();
        setAdapter();

    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(this, itemsList);
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
}