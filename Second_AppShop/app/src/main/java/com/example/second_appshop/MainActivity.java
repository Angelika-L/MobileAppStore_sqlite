package com.example.second_appshop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver MyBroadcastReceiver;
    IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyBroadcastReceiver = new MyBroadcastReceiver();

        intentFilter = new IntentFilter("com.example.second_appshop.EXAMPLE");
        registerReceiver(MyBroadcastReceiver, intentFilter, "android.permission.permis1", null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(MyBroadcastReceiver);
    }


}
