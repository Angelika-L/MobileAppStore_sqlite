package com.example.second_appshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if("com.example.second_appshop.EXAMPLE".equals(intent.getAction())) {
            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
            Intent in = new Intent(context, MyService.class);
            in.putExtra("name", intent.getStringExtra("com.example.second_appshop.name"));
            in.putExtra("price", intent.getStringExtra("com.example.second_appshop.price"));
            in.putExtra("quantity", intent.getStringExtra("com.example.second_appshop.quantity"));
            context.startService(in);


        }
    }


}
