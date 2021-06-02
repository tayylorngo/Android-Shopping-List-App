package com.taylorngo.shoppinglist;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements AddItemDialog.AddItemDialogListener, EditItemDialog.EditItemDialogListener {

    private RecyclerView recyclerView;
    private static RecyclerAdapter adapter;
    private AddItemDialog addItemDialog;

    private static SQLiteDatabase myDatabase;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SORT = "sort";
    public static final String SORT_ORDER = "sortOrder";
    private String sortBy;
    private String sortOrder;
    private static SharedPreferences sharedPreferences;

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

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings(v);
            }
        });
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        setAdapter();
    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(this, getAllItems());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sortBy = sharedPreferences.getString(SORT, "");
        sortOrder = sharedPreferences.getString(SORT_ORDER, "");
        sortList(sortBy, sortOrder);
    }

    public static void sortList(String sortBy, String sortOrder){
        if(sortOrder.equals("ascending")){
            sortOrder = " ASC";
        }
        else{
            sortOrder = " DESC";
        }
        if(sortBy.equals("name")){
            sortBy = ListDatabase.ListItemEntry.COLUMN_NAME;
        }
        else if(sortBy.equals("price")){
            sortBy = ListDatabase.ListItemEntry.COLUMN_PRICE;
        }
        else if(sortBy.equals("purchased status")){
            sortBy = ListDatabase.ListItemEntry.COLUMN_PURCHASED;
        }
        else{
            sortBy = ListDatabase.ListItemEntry.COLUMN_TIMESTAMP;
        }
        Cursor temp = myDatabase.query(ListDatabase.ListItemEntry.TABLE_NAME,
                null, null, null, null, null,
                sortBy +  sortOrder);
        adapter.swapCursor(temp);
    }

    private Cursor getAllItems(){
        return myDatabase.query(ListDatabase.ListItemEntry.TABLE_NAME,
                null, null, null, null, null,
                null
        );
    }

    public static void reQuery(){
        String sortBy = sharedPreferences.getString(SORT, "");
        String sortOrder = sharedPreferences.getString(SORT_ORDER, "");
        sortList(sortBy, sortOrder);
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
        cv.put(ListDatabase.ListItemEntry.COLUMN_PRICE, newItem.getPrice());
        cv.put(ListDatabase.ListItemEntry.COLUMN_DESCRIPTION, desc);
        cv.put(ListDatabase.ListItemEntry.COLUMN_PURCHASED, String.valueOf(purchased));
        myDatabase.insert(ListDatabase.ListItemEntry.TABLE_NAME, null, cv);
//        adapter.swapCursor(getAllItems());
        reQuery();
        addItemDialog.dismiss();
    }

    public static SQLiteDatabase getMyDatabase(){
        return myDatabase;
    }

    @Override
    public void applyTexts(String name, String price, String desc, boolean purchased, String category, long itemId) {
        ContentValues cv = new ContentValues();
        cv.put(ListDatabase.ListItemEntry.COLUMN_NAME, name);
        cv.put(ListDatabase.ListItemEntry.COLUMN_CATEGORY, category);
        cv.put(ListDatabase.ListItemEntry.COLUMN_PRICE, Double.parseDouble(price));
        cv.put(ListDatabase.ListItemEntry.COLUMN_DESCRIPTION, desc);
        cv.put(ListDatabase.ListItemEntry.COLUMN_PURCHASED, String.valueOf(purchased));
        myDatabase.update(ListDatabase.ListItemEntry.TABLE_NAME, cv, "_id=?", new String[]{String.valueOf(itemId)});
//        adapter.swapCursor(getAllItems());
        reQuery();
        adapter.getEditItemDialog().dismiss();
    }

    public void openSettings(View view){
        Intent intent = new Intent(this, activity_settings.class);
        startActivity(intent);
    }
}