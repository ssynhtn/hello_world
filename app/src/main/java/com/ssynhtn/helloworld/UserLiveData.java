package com.ssynhtn.helloworld;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ssynhtn.helloworld.model.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huangtongnao on 2018/3/29.
 */

public class UserLiveData extends MutableLiveData<User> {
    private static final String TAG = UserLiveData.class.getSimpleName();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onActive() {
        super.onActive();

        Log.d(TAG, "onActive");

    }

    @Override
    protected void onInactive() {
        super.onInactive();

        Log.d(TAG, "onInactive");
    }



    public void searchUsers(Observable<CharSequence> queries) {
        compositeDisposable.add(queries
                .debounce(200, TimeUnit.MILLISECONDS)
                .distinct()
                .switchMap(new Function<CharSequence, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(final CharSequence charSequence) throws Exception {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS)
                                .map(new Function<Long, User>() {
                                    @Override
                                    public User apply(Long aLong) throws Exception {
                                        return new User(aLong.intValue(), (int) (Math.random() * 100), charSequence.toString());
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        setValue(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "onComplete");
                    }
                }));

    }


}
