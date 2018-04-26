package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxBindingActivity extends AppCompatActivity {

    private static final String TAG = RxBindingActivity.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    private void test() {
        PublishSubject<String> subject = PublishSubject.create();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        Button button = findViewById(R.id.btn);
        disposable.add(RxView.clicks(button).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "click");
            }
        }));

        TextView textView = findViewById(R.id.text);
        disposable.add(RxTextView.textChanges(textView)
                .debounce(100, TimeUnit.MILLISECONDS)
//                .map(new Function<CharSequence, List<String>>() {
//                    @Override
//                    public List<String> apply(CharSequence charSequence) throws Exception {
//                        return search(charSequence);
//                    }
//                })
                .flatMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {
                        return searchRx(charSequence);
                    }
                })
//                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
//                    @Override
//                    public ObservableSource<List<String>> apply(CharSequence charSequence) throws Exception {

//                        return Observable.just(res);
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> res) throws Exception {

                        Log.d(TAG, "query " + res.get(0) + ", res " + res.subList(1, res.size()));
                    }
                }));
    }

    private List<String> search(CharSequence charSequence) {
        String query = charSequence.toString();
        try {
            Thread.sleep(Math.random() > 0.5 ? 1 : 1000);
        } catch (Exception ignored) {

        }

        List<String> res = new ArrayList<>();
        res.add(query);
        for (int i = 0; i < Math.min(3, query.length()); i++) {
            res.add(query.substring(0, i + 1));
        }
        return res;
    }

    private Observable<List<String>> searchRx(CharSequence charSequence) {
        return Observable.just(search(charSequence));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposable.clear();
    }
}
