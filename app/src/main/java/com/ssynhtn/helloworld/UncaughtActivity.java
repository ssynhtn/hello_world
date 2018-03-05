package com.ssynhtn.helloworld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UncaughtActivity extends AppCompatActivity {
    @BindView(R.id.tv_num)
    TextView tv_num;

    static AtomicInteger atomicInteger = new AtomicInteger();
    private static final String TAG = UncaughtActivity.class.getSimpleName();
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            Log.d(TAG, "t " + thread + ", throwable " + throwable.getMessage() + " current " + Thread.currentThread() + ", main: " + (Looper.myLooper() == Looper.getMainLooper()));
//            defaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncaught);

        ButterKnife.bind(this);
        Log.d(TAG, "default " + Thread.getDefaultUncaughtExceptionHandler());
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
//        tv_num.setText(getSomeText());


    }

    private String getSomeText() {
        String text = "fjaklsfjk";
        int a = Integer.parseInt(text);
        return Integer.toString(a);
    }

    @OnClick({R.id.btn_work, R.id.btn_toast, R.id.btn_run, R.id.btn_main})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_work: {
                new MyTask().execute();
                break;
            }
            case R.id.btn_run: {
                Log.d(TAG, "click run");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "running");
                        throw new RuntimeException("" + atomicInteger.getAndIncrement());
                    }
                }).start();
                break;
            }
            case R.id.btn_toast: {
                Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_main: {
                throw new RuntimeException();
            }
        }
    }


    private static class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            throw new RuntimeException("" + atomicInteger.getAndIncrement());
        }
    }
}
