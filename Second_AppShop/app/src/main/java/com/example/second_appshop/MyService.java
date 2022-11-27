package com.example.second_appshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class MyService extends Service{

    NotificationCompat NotificationCompat;
    final String channel_ID = "channel_1";
    final String channel_NAME = "Notification 1";
    int id = 0;
    private NotificationManagerCompat notificationManager;

    public MyService() {
    }

    public void onCreate(){
        createNotificationChannel();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        notification(intent);
        return super.onStartCommand(intent,flags,startId);
    }

    public void onDestroy(){
        super.onDestroy();
    }

    @Nullable
    @Override
    public android.os.IBinder onBind(Intent intent) {
        return null;
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new  NotificationChannel(channel_ID,channel_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is my channel");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


    public void notification(Intent intent) {
        Intent intent1 = new Intent();
        intent1.setComponent(new ComponentName("com.example.shopapp","com.example.shopapp.ListActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent1,0);
        Notification builder = new NotificationCompat.Builder(this, channel_ID)
                .setSmallIcon(R.drawable.ic_add_shopping_cart_black_24dp)
                .setContentTitle("Dodano nowy produkt!")
                .setContentText("Nazwa: "+ intent.getStringExtra("name") )
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Nazwa: "+ intent.getStringExtra("name")+", cena: "+ intent.getStringExtra("price")+", ilość: "+ intent.getStringExtra("quantity")))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id++, builder);

    }


}
