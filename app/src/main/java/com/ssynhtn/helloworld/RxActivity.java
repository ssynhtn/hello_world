package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {

//    private static Context leakingContext;

    private static final String TAG = RxActivity.class.getSimpleName();

    private Button button;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private RxPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        button = findViewById(R.id.btn);
        button.setOnClickListener(superOnClickListener);
//        test();
//        testButton();

//        testAndroidObservable();


//        RxActivity.leakingContext = this;
        testLeak();
//        testKill();

        presenter = new RxPresenterImpl();
        getLifecycle().addObserver(presenter);

        presenter.testLeak();
    }

    private void testKill() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RxActivity.this, "thread " + finalI, Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ignored) {

                    }
                }
            }
        }).start();
    }

    private void testLeak() {
//        Observable.fromArray("hello world google chrome android java kotlin scala apple pear orange".split(" "))
//                .zipWith(Observable.interval(1, TimeUnit.SECONDS), new BiFunction<String, Long, String>() {
//                    @Override
//                    public String apply(String s, Long aLong) throws Exception {
//                        return s;
//                    }
//                })

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
                        Toast.makeText(RxActivity.this, s, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "accept " + s);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void testAndroidObservable() {
//        AndroidObservable
    }

    private SuperOnClickListener superOnClickListener = new SuperOnClickListener();

    private class SuperOnClickListener implements View.OnClickListener {
        List<View.OnClickListener> onClickListeners = new ArrayList<>();

        @Override
        public void onClick(View v) {
            for (View.OnClickListener onClickListener : onClickListeners) {
                onClickListener.onClick(v);
            }
        }

        public void addOnClickListener(View.OnClickListener onClickListener) {
            onClickListeners.add(onClickListener);
        }
    }


    private void testButton() {
        ConnectableObservable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emitter.onNext("click on " + button.getText() + ", listener " + this);
                    }
                });
            }
        }).mergeWith(Observable.just("hello")).publish();



        println("created observable " + observable);


        Consumer<String> firstConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                println(this + " accept " + s);
            }
        };

        Consumer<String> secondConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                println(this + " accept " + s);
            }
        };

        println("firstConsumer " + firstConsumer);
        println("second " + secondConsumer);

        observable.subscribe(firstConsumer);

        observable.connect();


    }

    private void test() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                println("emmit hello");
                emitter.onNext("hello");
                println("emmit world");
                emitter.onNext("world");
                println("emmit complete");
                emitter.onComplete();
                println("try to emmit again");
                emitter.onNext("again");
            }
        });

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                println("accept " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                println("onComplete");
            }
        });
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void println(String text) {
        Log.d(TAG, currentTime() + " " + Thread.currentThread() + " " + text);
    }

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss:SS", Locale.getDefault());
    private String currentTime() {
        return FORMAT.format(new Date());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
