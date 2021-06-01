package com.taylorngo.shoppinglist;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener {

    private RecyclerView recyclerView;
    private static RecyclerAdapter adapter;
    private AddItemDialog addItemDialog;

    private static SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListDBHelper dbHelper = new ListDBHelper(this);
        myDatabase = dbHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.shoppingList);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        setAdapter();
    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(this, getAllItems());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private Cursor getAllItems(){
        return myDatabase.query(ListDatabase.ListItemEntry.TABLE_NAME,
                null, null, null, null, null,
                ListDatabase.ListItemEntry.COLUMN_TIMESTAMP +  " ASC"
        );
    }

    private void addItem(){
        openAddItemDialog();
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

        ContentValues cv = new ContentValues();
        cv.put(ListDatabase.ListItemEntry.COLUMN_NAME, name);
        cv.put(ListDatabase.ListItemEntry.COLUMN_CATEGORY, category);
        cv.put(ListDatabase.ListItemEntry.COLUMN_PRICE, price);
        cv.put(ListDatabase.ListItemEntry.COLUMN_DESCRIPTION, desc);
        cv.put(ListDatabase.ListItemEntry.COLUMN_PURCHASED, String.valueOf(purchased));
        myDatabase.insert(ListDatabase.ListItemEntry.TABLE_NAME, null, cv);
        adapter.swapCursor(getAllItems());
        addItemDialog.dismiss();
    }

    public static SQLiteDatabase getMyDatabase(){
        return myDatabase;
    }

    public static RecyclerAdapter getAdapter(){
        return adapter;
    }
}