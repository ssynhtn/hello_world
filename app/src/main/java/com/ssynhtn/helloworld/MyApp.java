package com.ssynhtn.helloworld;

import android.app.ActivityManager;
import android.app.Application;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import java.util.List;

/**
 * Created by huangtongnao on 2017/11/13.
 */

public class MyApp extends Application {
    private static final String TAG = MyApp.class.getSimpleName();

    private AppLifeCycleObserver appLifeCycleObserver;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);

        instance = this;

        Log.d(TAG, "hello process name " + getProcessName() + ", applicationId " + BuildConfig.APPLICATION_ID + ", package " + getPackageName());

        appLifeCycleObserver = new AppLifeCycleObserver(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifeCycleObserver);

    }

    private String getProcessName() {
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo myProcess = null;
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = null;
        if (activityManager != null) runningProcesses = activityManager.getRunningAppProcesses();
        if(runningProcesses != null) {

            for (ActivityManager.RunningAppProcessInfo process : runningProcesses) {
                if (process.pid == myPid) {
                    myProcess = process;
                    break;
                }
            }
        }

        if (myProcess != null) {
            return myProcess.processName;
        }

        return "";
    }



    static Context instance;
    public static Context getInstance() {
        return instance;
    }
}
