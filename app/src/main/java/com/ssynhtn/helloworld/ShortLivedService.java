package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ShortLivedService extends Service {
    private static final String TAG = ShortLivedService.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    public static void start(Context context) {
        Intent intent = new Intent(context, ShortLivedService.class);
        context.startService(intent);
    }

    public ShortLivedService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSelf(startId);
            }
        }, 30 * 1000);

        Disposable d = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "TICK " + aLong);
                    }
                });

        disposable.add(d);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disposable.dispose();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
