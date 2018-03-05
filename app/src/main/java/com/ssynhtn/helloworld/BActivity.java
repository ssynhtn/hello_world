package com.ssynhtn.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_b, R.id.btn_a})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_b: {
                Intent intent = new Intent(this, BActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_a: {
                Intent intent = new Intent(this, AlarmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            }

            default: break;
        }
    }
}
