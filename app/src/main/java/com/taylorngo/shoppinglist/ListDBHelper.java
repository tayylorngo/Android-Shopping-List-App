package com.taylorngo.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.taylorngo.shoppinglist.ListDatabase.*;
import android.support.annotation.Nullable;

public class ListDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shoppingList.db";
    public static final int DATA_BASE_VERSION = 1;

    public ListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " +
                ListItemEntry.TABLE_NAME + " (" +
                ListItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ListItemEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ListItemEntry.COLUMN_PRICE + " DOUBLE NOT NULL, " +
                ListItemEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                ListItemEntry.COLUMN_PURCHASED + " TEXT NOT NULL, " +
                ListItemEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                ListItemEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_SHOPPINGLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ListItemEntry.TABLE_NAME);
        onCreate(db);
    }
}
