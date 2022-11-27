package com.example.shopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetActivity extends AppCompatActivity {

    private CheckBox ChbBlack;
    private CheckBox ChbRed;
    private CheckBox ChbBlue;
    private SharedPreferences SharedPreferences;
    private SharedPreferences.Editor SharedPreferencesEditor;
    private TextView Opcje;
    private TextView RozmCz;
    private TextView KolorTV;
    private EditText EnterSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ChbBlack = findViewById(R.id.checkBox);
        ChbRed = findViewById(R.id.checkBox3);
        ChbBlue = findViewById(R.id.checkBox2);
        Opcje = findViewById(R.id.textView4);
        RozmCz = findViewById(R.id.textView5);
        KolorTV = findViewById(R.id.textView);
        EnterSize = findViewById(R.id.EnterSize);

        SharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferencesEditor = SharedPreferences.edit();

        setChangeColor(ChbBlack, ChbRed, ChbBlue);
        setChangeSize(EnterSize);
    }


    protected void onStart() {
        super.onStart();
        Boolean boolBlack = SharedPreferences.getBoolean("ChbBlack", false);
        ChbBlack.setChecked(boolBlack);

        Boolean boolRed = SharedPreferences.getBoolean("ChbRed", false);
        ChbRed.setChecked(boolRed);

        Boolean boolBlue = SharedPreferences.getBoolean("ChbBlue", false);
        ChbBlue.setChecked(boolBlue);

        String strSize = SharedPreferences.getString("EnterSize", "");
        EnterSize.setText(strSize);

        setChangeColor(ChbBlack, ChbRed, ChbBlue);
        setChangeSize(EnterSize);
    }


    public void click(View view) {

        SharedPreferencesEditor.putString("EnterSize", EnterSize.getText() + "");
        SharedPreferencesEditor.putBoolean("ChbBlack", ChbBlack.isChecked());
        SharedPreferencesEditor.putBoolean("ChbRed", ChbRed.isChecked());
        SharedPreferencesEditor.putBoolean("ChbBlue", ChbBlue.isChecked());
        SharedPreferencesEditor.apply();


        String tekxt = "Opcje zostały zapisane.";
        Toast msg = Toast.makeText(getBaseContext(), tekxt, Toast.LENGTH_LONG);
        msg.show();

        setChangeColor(ChbBlack, ChbRed, ChbBlue);
        setChangeSize(EnterSize);

    }

    private void setChangeSize(EditText EnterSize) {
        if (EnterSize.getText().toString().matches("")) {
            Opcje.setTextSize(17);
            RozmCz.setTextSize(17);
            KolorTV.setTextSize(17);
        } else {
            float size = Float.valueOf(EnterSize.getText().toString());
            Opcje.setTextSize(size);
            RozmCz.setTextSize(size);
            KolorTV.setTextSize(size);
        }
    }

    private void setChangeColor(CheckBox ChbBlack, CheckBox ChbRed, CheckBox ChbBlue) {
        if (ChbBlack.isChecked()) {
            ChbBlack.setTextColor(Color.BLACK);
            ChbBlue.setTextColor(Color.BLACK);
            ChbRed.setTextColor(Color.BLACK);
            Opcje.setTextColor(Color.BLACK);
            RozmCz.setTextColor(Color.BLACK);
            KolorTV.setTextColor(Color.BLACK);
        } else if (ChbRed.isChecked()) {
            ChbRed.setTextColor(Color.RED);
            ChbBlue.setTextColor(Color.RED);
            ChbBlack.setTextColor(Color.RED);
            Opcje.setTextColor(Color.RED);
            RozmCz.setTextColor(Color.RED);
            KolorTV.setTextColor(Color.RED);
        } else if (ChbBlue.isChecked()) {
            ChbBlue.setTextColor(Color.BLUE);
            ChbRed.setTextColor(Color.BLUE);
            ChbBlack.setTextColor(Color.BLUE);
            Opcje.setTextColor(Color.BLUE);
            RozmCz.setTextColor(Color.BLUE);
            KolorTV.setTextColor(Color.BLUE);
        } else {
            ChbBlack.setTextColor(Color.BLACK);
            ChbBlue.setTextColor(Color.BLACK);
            ChbRed.setTextColor(Color.BLACK);
            Opcje.setTextColor(Color.BLACK);
            RozmCz.setTextColor(Color.BLACK);
            KolorTV.setTextColor(Color.BLACK);
        }
    }

    public void changeCheckboxOne(View view) {

        switch (view.getId()) {
            case R.id.checkBox: //black
                ChbBlue.setChecked(false);
                ChbRed.setChecked(false);
                break;
            case R.id.checkBox3: //red
                ChbBlack.setChecked(false);
                ChbBlue.setChecked(false);
                break;
            case R.id.checkBox2://blue
                ChbBlack.setChecked(false);
                ChbRed.setChecked(false);
                break;
        }
    }

}