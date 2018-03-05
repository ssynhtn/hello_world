package com.ssynhtn.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BatteryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_b})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_start: {
                Intent service = new Intent(this, BatteryService.class);
                startService(service);
                break;
            }
            case R.id.btn_stop: {
                Intent service = new Intent(this, BatteryService.class);
                stopService(service);
                break;
            }
            case R.id.btn_b: {
                Intent b = new Intent("com.hello");
                sendBroadcast(b);
                break;
            }
        }
    }
}
