package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StartedAndBoundService extends Service {
    private static final String TAG = StartedAndBoundService.class.getSimpleName();

    public StartedAndBoundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onbind");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onunbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onrebind");
        super.onRebind(intent);
    }

    public void hello() {
        Log.d(TAG, "hello");
    }

    public class MyBinder extends Binder {
        public StartedAndBoundService getService() {
            return StartedAndBoundService.this;
        }
    }
}
