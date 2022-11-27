package com.example.shopapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.BaseColumns;


public class ListaItem extends Activity {

    private String name;
    private int price;
    private int quantity;
    private int checked;
    private boolean checked_bool;
    private String id_product;

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }
    public boolean getChecked_bool() {
        return checked_bool;
    }
    public String getId() {
        return id_product;
    }

    public void setChecked_bool(boolean checked_bool) {
        this.checked_bool = checked_bool;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setId(String id_product) {
        this.id_product = id_product;
    }

    public  ListaItem() {
    }

    public  ListaItem(String name, int price, int quantity, boolean checked_bool, String id_product) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked_bool = checked_bool;
        this.id_product = id_product;
    }


    public static final class ListaItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "ListaProduktow";
        public static final String COL_NAME = "name";
        public static final String COL_PRICE = "price";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_CHB_SALE = "sale_chb";
        public static final String COL_TIMESTAMP = "timestamp";
        public static final String _ID = "id";

    }

    @Override
    public String toString() {
        return "ListaItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", checked_bool=" + checked_bool +
                '}';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
    }

}









