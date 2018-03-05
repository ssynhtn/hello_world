package com.ssynhtn.helloworld;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class FrameAnimationActivity extends AppCompatActivity {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        view = findViewById(R.id.animation_view);
        AnimationDrawable drawable = (AnimationDrawable) view.getBackground();
        drawable.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        AnimationDrawable drawable = (AnimationDrawable) view.getBackground();
        boolean isRunning = drawable.isRunning();
        Toast.makeText(this, "animation is running? " + isRunning, Toast.LENGTH_SHORT).show();
//        if (!isRunning)
//            drawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable drawable = (AnimationDrawable) view.getBackground();
                boolean isRunning = drawable.isRunning();
                Toast.makeText(FrameAnimationActivity.this, "in foreground animation is running? " + isRunning, Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable drawable = (AnimationDrawable) view.getBackground();
                boolean isRunning = drawable.isRunning();
                Toast.makeText(FrameAnimationActivity.this, "in background animation is running? " + isRunning, Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }
}
