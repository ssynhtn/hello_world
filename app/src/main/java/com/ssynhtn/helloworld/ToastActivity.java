package com.ssynhtn.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        findViewById(R.id.btn_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleToast(ToastActivity.this, "" + System.currentTimeMillis());
            }
        });

        findViewById(R.id.btn_my).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myToast("" + System.currentTimeMillis());
            }
        });

    }

    private void my() {
        for (int i = 0; i < 10; i++) {
            myToast("i " + i);
        }
    }

    private static Toast sToastInstance;
    private void myToast(String text) {
        if (sToastInstance != null) {
            sToastInstance.cancel();
        }
        sToastInstance = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        sToastInstance.show();
    }

    private void comment() {
        for (int i = 0; i < 10; i++) {
            showSingleToast(this, "i " + i);
        }
    }

    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static Toast sToast;
    public static void showSingleToast(final Context context, final String text) {
        if (sToast == null) {

            sToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }

        sToast.setText(text);
        sToast.show();

    }
}
