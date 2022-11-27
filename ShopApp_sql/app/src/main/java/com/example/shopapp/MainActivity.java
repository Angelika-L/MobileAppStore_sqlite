package com.example.shopapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;   //do przejścia na inny widok



public class MainActivity extends Activity {

    //private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.button:
                Intent intentL = new Intent(this, ListActivity.class);    //przechodzenie do "listy" Activity
                startActivity(intentL);
                break;
            case R.id.button2:
                Intent intentS = new Intent(this, SetActivity.class);     ///przechodzenie do "opcji" Activity
                startActivity(intentS);
                break;
        }

    }

}