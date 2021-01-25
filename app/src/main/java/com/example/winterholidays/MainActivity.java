package com.example.winterholidays;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Intent mIntent;
    MyService myService;
    boolean bound;
    ProgreesReciver progreesReciver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(this, MyService.class);
        ProgressBar progressBar = findViewById(R.id.ma_pb);
        OnProgressListener onProgressListener = new OnProgressListener() {
            @Override
            public void updateProgress(int progress) {
                progressBar.setProgress(progress);
            }
        };
        progreesReciver = new ProgreesReciver(onProgressListener);
        intentFilter = new IntentFilter(progreesReciver.SIMPLE_ACTION);


        bindService(mIntent, sConn, 0);
        startService(mIntent);

        findViewById(R.id.ma_button).setOnClickListener(v -> {
            if (!bound) return;
            myService.downProgress();

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(progreesReciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(progreesReciver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!bound) return;
        unbindService(sConn);
        bound = false;
    }

    ServiceConnection sConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((MyService.MyBinder) service).getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };
}

