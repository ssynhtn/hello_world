package com.ssynhtn.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    private static final String TAG = BatteryReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "onReceive " + intent.getAction(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "" + intent.getAction());
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction()) || Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {

            Intent service = new Intent(context, BatteryService.class);
            service.setAction(intent.getAction());
            context.startService(service);
        } else if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            Toast.makeText(context, "tick", Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            Log.d(TAG, "battery changed");
        }
    }
}
