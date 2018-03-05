package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BatteryService extends Service {
    private static final String TAG = BatteryService.class.getSimpleName();

    static class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "receive " + intent);
        }
    }

    private Receiver receiver;

    public BatteryService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new Receiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        IntentFilter filter1 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(receiver, filter);
        registerReceiver(receiver, filter1);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand " + intent.getAction(), Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
