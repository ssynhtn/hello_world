package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartedAndBoundServiceActivity extends AppCompatActivity {

    private static final String TAG = StartedAndBoundServiceActivity.class.getSimpleName();
    private boolean isBind;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceconnected");
            isBind = true;
            StartedAndBoundService.MyBinder myBinder = (StartedAndBoundService.MyBinder) iBinder;
            myBinder.getService().hello();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_and_bound_service);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind, R.id.btn_activity})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_start: {
                Intent start = new Intent(this, StartedAndBoundService.class);
                startService(start);
                break;
            }
            case R.id.btn_stop: {
                Intent start = new Intent(this, StartedAndBoundService.class);
                stopService(start);
                break;
            }
            case R.id.btn_bind: {
                Intent start = new Intent(this, StartedAndBoundService.class);
                bindService(start, connection, Context.BIND_AUTO_CREATE);
                break;
            }
            case R.id.btn_unbind: {
                if (isBind) {
                    isBind = false;
                    unbindService(connection);
                }

                break;
            }

            case R.id.btn_activity: {
                Intent intent = new Intent(this, StartedAndBoundServiceActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isBind) {
            isBind = false;
            unbindService(connection);
        }
    }
}
