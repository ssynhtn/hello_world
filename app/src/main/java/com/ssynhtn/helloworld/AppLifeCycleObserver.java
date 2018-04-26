package com.ssynhtn.helloworld;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by huangtongnao on 2018/3/29.
 */

public class AppLifeCycleObserver implements LifecycleObserver {
    private static final String TAG = AppLifeCycleObserver.class.getSimpleName();

    private Context context;

    public AppLifeCycleObserver(Context context) {
        this.context = context.getApplicationContext();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onAppCreate() {
        logEvent("create");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppStart() {
        logEvent("start");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAppResume() {
        logEvent("resume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onAppPause() {
        logEvent("pause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppStop() {
        logEvent("stop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onAppDestroy() {
        logEvent("destroy");
    }

    private void logEvent(String text) {
        Log.d(TAG, "event " + text);
        Toast.makeText(context, "event " + text, Toast.LENGTH_SHORT).show();
    }

}
