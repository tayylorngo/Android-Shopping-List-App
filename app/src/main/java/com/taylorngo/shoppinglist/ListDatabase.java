package com.taylorngo.shoppinglist;

import android.provider.BaseColumns;

public class ListDatabase {

    private ListDatabase(){}

    public static final class ListItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "shoppingList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_PURCHASED = "purchased";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
