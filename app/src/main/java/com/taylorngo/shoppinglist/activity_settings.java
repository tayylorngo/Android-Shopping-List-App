package com.taylorngo.shoppinglist;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class activity_settings extends AppCompatActivity {

    private Spinner sortBySelector;
    private Spinner sortOrderSelector;
    private Button setButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SORT = "sort";
    public static final String SORT_ORDER = "sortOrder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String currSortField = sharedPreferences.getString(SORT, "");
        String currSortOrder = sharedPreferences.getString(SORT, "");

        String[] sortOptions = {"default", "name", "price", "purchased status"};
        String[] sortOrders = {"ascending", "descending"};

        ArrayList<String> sortOptions2 = new ArrayList<String>(Arrays.asList(sortOptions));
        ArrayList<String> sortOrders2 = new ArrayList<String>(Arrays.asList(sortOrders));

        this.sortBySelector = findViewById(R.id.sortSelector);
        this.sortOrderSelector = findViewById(R.id.sortOrderSelector);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortBySelector.setAdapter(adapter);
        sortBySelector.setSelection(sortOptions2.indexOf(currSortField));

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, sortOrders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sortOrderSelector.setAdapter(adapter);
        sortOrderSelector.setSelection(sortOrders2.indexOf(currSortOrder));

        this.setButton = findViewById(R.id.setSettingsButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT, this.sortBySelector.getSelectedItem().toString());
        editor.putString(SORT_ORDER, this.sortOrderSelector.getSelectedItem().toString());
        editor.apply();
        this.finish();
    }

}