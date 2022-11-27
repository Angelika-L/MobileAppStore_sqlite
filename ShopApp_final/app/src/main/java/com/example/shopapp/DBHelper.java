package com.example.shopapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DB_NAME = "lista_produktow.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + ListaItem.ListaItemEntry.TABLE_NAME + " ("+
                ListaItem.ListaItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ListaItem.ListaItemEntry.COL_NAME + " TEXT NOT NULL, "+
                ListaItem.ListaItemEntry.COL_PRICE+" INTIGER NOT NULL, "+
                ListaItem.ListaItemEntry.COL_QUANTITY+" INTIGER NOT NULL, "+
                ListaItem.ListaItemEntry.COL_CHB_SALE+" INTIGER DEFAULT 0, "+
                ListaItem.ListaItemEntry.COL_TIMESTAMP+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                "); ";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ListaItem.ListaItemEntry.TABLE_NAME);
            onCreate(db);
    }





}
