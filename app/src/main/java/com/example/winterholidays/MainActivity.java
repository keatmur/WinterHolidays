package com.example.winterholidays;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private static String CHANNEL_ID="WinterHolidays";
    private  static  int importance = NotificationManager.IMPORTANCE_LOW;
    private int number=0;
    private NotificationManager notificationManager;
    private   NotificationCompat.Builder nBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
         nBuilder = getNotificationBilder();
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener( v -> {
            getNotification(number);
            Log.i("Notification","clicl bootom" ) ;
            number++;
        });

    }
    private NotificationCompat.Builder getNotificationBilder() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.O)
        {
            return  new NotificationCompat.Builder(this);
        } else
        {
            if (notificationManager.getNotificationChannel(CHANNEL_ID)==null) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "WinterHolidays", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }
            return new NotificationCompat.Builder(this, CHANNEL_ID);
        }

    }

    private void getNotification(int number){
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0,notificationIntent, 0);
       nBuilder.setContentIntent(contentIntent)
               .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notifiction")
                .setContentText("This notification №"+ number)
                .setAutoCancel(true);
        notificationManager.notify(number,nBuilder.build());

    }
}

