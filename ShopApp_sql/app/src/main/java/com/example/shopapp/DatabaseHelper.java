package com.example.shopapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*


public class DatabaseHelper extends SQLiteOpenHelper {

  /*  private static final String DB_NAME = "ListaProduktow";
    public static final int DB_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase sqlBD){                                 //utworzenie tabeli z produktami listy
        final String createTable_Product = "CREATE TABLE "+
                ListaItem.ListaItemEntry.TABLE_NAME +" ("+
                ListaItem.ListaItemEntry.COL_ID+ "  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ListaItem.ListaItemEntry.COL_NAME + "TEXT NOT NULL, "+
                ListaItem.ListaItemEntry.COL_PRICE + " IMTEGER, "+
                ListaItem.ListaItemEntry.COL_QUANTITY + " INTEGER, "+
                ListaItem.ListaItemEntry.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                ");";
        sqlBD.execSQL(createTable_Product);
    }

    public void onUpgrade(SQLiteDatabase sqlBD, int oldVersion , int newVersion){
        sqlBD.execSQL("DROP TABLE IF EXISTS "+ ListaItem.ListaItemEntry.TABLE_NAME+";");
        onCreate(sqlBD);
    }
*/


}*/