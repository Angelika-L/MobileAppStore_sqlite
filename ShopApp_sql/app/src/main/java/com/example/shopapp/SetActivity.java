package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends AppCompatActivity {

    private CheckBox ChbBlack;             //checkBox
    private CheckBox ChbRed;               //checkBox3
    private CheckBox ChbBlue;              //checkBox2
    private SharedPreferences SharedPreferences;                //checkBox Color = BLACK
    private SharedPreferences.Editor SharedPreferencesEditor;
    private SharedPreferences SharedPreferences2;                //checkBox3 Color = RED
    private SharedPreferences.Editor SharedPreferencesEditor2;
    private SharedPreferences SharedPreferences3;                //checkBox2 Color = BLUE
    private SharedPreferences.Editor SharedPreferencesEditor3;
    private TextView Opcje;
    private TextView RozmCz;
    private TextView KolorTV;
    private EditText EnterSize;
    private SharedPreferences SharedPreferencesES;                //Size czcionki
    private SharedPreferences.Editor SharedPreferencesEditorET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        setContentView(R.layout.activity_set);
        ChbBlack = findViewById(R.id.checkBox);                 //Checkbox Color = BLACK
        SharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferencesEditor = SharedPreferences.edit();

        ChbRed = findViewById(R.id.checkBox3);                 //Checkbox Color = RED
        SharedPreferences2 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferencesEditor2 = SharedPreferences2.edit();

        ChbBlue = findViewById(R.id.checkBox2);                 //Checkbox Color = BLUE
        SharedPreferences3 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferencesEditor3 = SharedPreferences3.edit();

        Opcje = findViewById(R.id.textView4);
        RozmCz = findViewById(R.id.textView5);
        KolorTV = findViewById(R.id.textView);


        EnterSize = findViewById(R.id.EnterSize);                 //Change Size
        SharedPreferencesES = getPreferences(Context.MODE_PRIVATE);
        SharedPreferencesEditorET = SharedPreferencesES.edit();

        setChangeColor(ChbBlack,ChbRed,ChbBlue); //wywoanie metody zmian kolorów
        setChangeSize(EnterSize);    //wywoanie metody zmian size
    }


   protected void onStart(){
        super.onStart();
        Boolean boolBlack = SharedPreferences.getBoolean("ChbBlack", false ); //Checkbox Color = BLACK
        ChbBlack.setChecked(boolBlack);

        Boolean boolRed = SharedPreferences2.getBoolean("ChbRed", false ); //Checkbox Color = BLACK
        ChbRed.setChecked(boolRed);

       Boolean boolBlue = SharedPreferences3.getBoolean("ChbBlue", false ); //Checkbox Color = BLACK
       ChbBlue.setChecked(boolBlue);

       String strSize = SharedPreferencesES.getString("EnterSize",""); //Change size
       EnterSize.setText(strSize);

       setChangeColor(ChbBlack,ChbRed,ChbBlue); //wywoanie metody zmian kolorów
       setChangeSize(EnterSize);    //wywoanie metody zmian size
   }


   public void click(View view) {

       SharedPreferencesEditorET.putString("EnterSize", EnterSize.getText()+""); //Size
       SharedPreferencesEditorET.apply();
       SharedPreferencesEditor.putBoolean("ChbBlack", ChbBlack.isChecked()); //Color
       SharedPreferencesEditor2.putBoolean("ChbRed", ChbRed.isChecked());
       SharedPreferencesEditor3.putBoolean("ChbBlue", ChbBlue.isChecked());
       SharedPreferencesEditor.apply();
       SharedPreferencesEditor2.apply();
       SharedPreferencesEditor3.apply();

       String tekxt = "Opcje zostały zapisane.";
       Toast msg = Toast.makeText(getBaseContext(), tekxt, Toast.LENGTH_LONG);
       msg.show();

       setChangeColor(ChbBlack,ChbRed,ChbBlue); //wywoanie metody zmian kolorów
       setChangeSize(EnterSize);    //wywoanie metody zmian size

   }


    //metoda zmiany w opcjach rozmiaru:
    private void setChangeSize(EditText EnterSize) {
        if(EnterSize.getText().toString().matches("")){
            //float size = 17.0F  domyślne ustawienie
            Opcje.setTextSize(17);
            RozmCz.setTextSize(17);
            KolorTV.setTextSize(17);
        }
        else {
            float size = Float.valueOf(EnterSize.getText().toString());
            Opcje.setTextSize(size);
            RozmCz.setTextSize(size);
            KolorTV.setTextSize(size);
        }
    }


    //metoda zmiany w opcjach koloru:
   private void setChangeColor(CheckBox ChbBlack, CheckBox ChbRed, CheckBox ChbBlue){
       if(ChbBlack.isChecked()) {
           ChbBlack.setTextColor(Color.BLACK);
           ChbBlue.setTextColor(Color.BLACK);ChbRed.setTextColor(Color.BLACK);
           Opcje.setTextColor(Color.BLACK);
           RozmCz.setTextColor(Color.BLACK);
           KolorTV.setTextColor(Color.BLACK);
       }
       else if (ChbRed.isChecked()) {
           ChbRed.setTextColor(Color.RED);
           ChbBlue.setTextColor(Color.RED);ChbBlack.setTextColor(Color.RED);
           Opcje.setTextColor(Color.RED);
           RozmCz.setTextColor(Color.RED);
           KolorTV.setTextColor(Color.RED);
       }
       else if (ChbBlue.isChecked()) {
           ChbBlue.setTextColor(Color.BLUE);ChbRed.setTextColor(Color.BLUE); ChbBlack.setTextColor(Color.BLUE);
           Opcje.setTextColor(Color.BLUE);
           RozmCz.setTextColor(Color.BLUE);
           KolorTV.setTextColor(Color.BLUE);
       }
       else {
           ChbBlack.setTextColor(Color.BLACK);
           ChbBlue.setTextColor(Color.BLACK);ChbRed.setTextColor(Color.BLACK);
           Opcje.setTextColor(Color.BLACK);
           RozmCz.setTextColor(Color.BLACK);
           KolorTV.setTextColor(Color.BLACK);
       }
   }


    //metoda pilnująca aby checkbox byl zaznaczony tylko jeden
    public void changeCheckboxOne(View view) {

          switch(view.getId()) {
              case R.id.checkBox: //black
                  ChbBlue.setChecked(false);
                  ChbRed.setChecked(false);
                  break;
              case R.id.checkBox3: //red
                  ChbBlack.setChecked(false);
                  ChbBlue.setChecked(false);
                  break;
              case R.id.checkBox2: //blue
                  ChbBlack.setChecked(false);
                  ChbRed.setChecked(false);
                  break;
          }
      }









}