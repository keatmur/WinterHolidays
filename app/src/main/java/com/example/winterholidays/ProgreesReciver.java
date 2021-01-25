package com.example.winterholidays;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProgreesReciver extends BroadcastReceiver {
    OnProgressListener onProgressListener;
    final String SIMPLE_ACTION = "com.example.winterholidays.UP_PROGRESS";
    final String reciverMessg = "com.example.winterholidays.PROGRESS_RECIVER";

    public ProgreesReciver(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        onProgressListener.updateProgress(intent.getIntExtra(reciverMessg, 0));

    }
}
