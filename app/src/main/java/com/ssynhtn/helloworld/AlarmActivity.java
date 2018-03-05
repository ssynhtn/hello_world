package com.ssynhtn.helloworld;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AlarmActivity extends AppCompatActivity {

    private static final String TAG = AlarmActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        ButterKnife.bind(this);
        test();

//        Intent intent = new Intent(this, SimpleReceiver.class);
//        sendBroadcast(intent);
    }

    private void test() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "is working " + Thread.currentThread());
                emitter.onNext("");
                Thread.sleep(1000);
                Log.d(TAG, "work done");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Log.d(TAG, "void map " + Thread.currentThread());
                        return "hello";
                    }
                })
                .subscribeOn(Schedulers.io()).subscribe();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emitter on " + Thread.currentThread());
                Thread.sleep(1000);
                emitter.onNext("hello");
                Thread.sleep(1000);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(AlarmActivity.this, "res " + s, Toast.LENGTH_SHORT).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Toast.makeText(AlarmActivity.this, "complete", Toast.LENGTH_SHORT).show();
            }
        });

        Observable.just("hello").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(AlarmActivity.this, "thread " + Thread.currentThread() + s, Toast.LENGTH_SHORT).show();
            }
        });

        Observable.just("world").observeOn(Schedulers.io()).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                Log.d(TAG, "map on " + Thread.currentThread());
                return s.toUpperCase();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "sub on " + Thread.currentThread());
            }
        });
    }

    @OnClick({R.id.btn_3, R.id.btn_10, R.id.btn_1m, R.id.btn_b})
    public void onClickView(View view) {
        int seconds = 0;
        switch (view.getId()) {
            case R.id.btn_3: {
                seconds = 3;
                break;
            }
            case R.id.btn_10: {
                seconds = 10;
                break;
            }
            case R.id.btn_1m: {
                seconds = 60;
                break;
            }

            case R.id.btn_b: {
                Intent intent = new Intent(this, BActivity.class);
                startActivity(intent);
                break;
            }
            default: break;
        }

        if (seconds > 0) {
            setAlarmAfter(seconds);
        }
    }

    private void setAlarmAfter(int seconds) {
        Calendar calendar = Calendar.getInstance();
        long triggerTime = calendar.getTimeInMillis() + 1000 * seconds;

        Intent intent = new Intent(this, SimpleReceiver.class);
        intent.putExtra("random", Math.random());
        intent.putExtra("model", ParcelableUtil.marshall(new NotificationModel()));
        PendingIntent pi = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);     // first 0 is id, second 0 is flag(don't know what use)

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            Toast.makeText(this, "set alarm after " + seconds, Toast.LENGTH_SHORT).show();
            manager.set(AlarmManager.RTC_WAKEUP, triggerTime, pi);
        }

    }


}
