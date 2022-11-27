package com.example.shopapp;

import android.app.Activity;
import android.os.Bundle;


public class ListaItem extends Activity {
    private String name;
    private double price;
    private int quantity;
    private boolean checked;
    private int identyfikator;


    public ListaItem(String name, Double price, int quantity, boolean checked, int identyfikator) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
        this.identyfikator = identyfikator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getIdentyfikator() { return identyfikator;  }
    public void setIdentyfikator(int checked) {
        this.identyfikator = checked;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
    }
}