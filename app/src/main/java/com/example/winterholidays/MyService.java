package com.example.winterholidays;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    final String LOG_TAG = "myLogs";
    final String SIMPLE_ACTION = "com.example.winterholidays.UP_PROGRESS";
    final String reciverMessg = "com.example.winterholidays.PROGRESS_RECIVER";

    final int[] procent = {0};

    private ScheduledExecutorService services;
    MyBinder binder;
    Intent mIntent;

    public MyService() {

    }

    @Override
    public void onCreate() {
        services = Executors.newSingleThreadScheduledExecutor();
        mIntent = new Intent(SIMPLE_ACTION);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        services.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {

                procent[0] = procent[0] + 5;
                if (procent[0] > 100) procent[0] = 100;
                mIntent.putExtra(reciverMessg, procent[0]);
                mIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(mIntent);


            }
        }, 0, 2, TimeUnit.SECONDS);

        return super.onStartCommand(intent, flags, startId);
    }


    public void downProgress() {
        int randomNum = 1 + (int) (Math.random() * 100);
        procent[0] = procent[0] - randomNum;
        if (procent[0] < 0) {
            procent[0] = 0;
        }

        mIntent.putExtra(reciverMessg, procent[0]);
        mIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(mIntent);

    }


    @Override
    public void onDestroy() {
        services.shutdown();
        if (services.isTerminated() == true) Log.i(LOG_TAG, "Excuters stop");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            binder = new MyBinder();
        }
        return binder;
    }

    class MyBinder extends Binder {

        MyService getService() {
            return MyService.this;
        }
    }
}