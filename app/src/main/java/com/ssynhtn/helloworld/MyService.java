package com.ssynhtn.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public MyBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public void hello() {
        Toast.makeText(this, "hello!", Toast.LENGTH_SHORT).show();
    }

    class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }

    }
}
