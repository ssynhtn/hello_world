package com.ssynhtn.helloworld;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huangtongnao on 2018/3/16.
 */

public class RxPresenterImpl implements RxPresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = RxPresenterImpl.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onLifeCycleEvent(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(TAG, "life cycle changed " + owner + ", event " + event);

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(LifecycleOwner owner) {
        Log.d(TAG, "onPause " + owner);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        compositeDisposable.dispose();
    }

    @Override
    public void testLeak() {
        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return aLong.toString();
                    }
                })
//                .take(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MyApp.getInstance(), s, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "accept " + s);
                    }
                });


        compositeDisposable.add(disposable);
    }
}
