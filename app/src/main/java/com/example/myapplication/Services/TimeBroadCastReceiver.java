package com.example.myapplication.Services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.time.temporal.TemporalAccessor;

public class TimeBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int time = intent.getIntExtra(FileManagerService.TIME_EXTRA, -1);
        Log.d(FileManagerService.TAG, "onReceive() - time received by broadcast: " + time);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private TimeBroadCastReceiver mTimeBroadCastReceiver = new TimeBroadCastReceiver();


}