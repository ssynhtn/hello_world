package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RotateActivity extends AppCompatActivity {

    @BindView(R.id.view)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.left, R.id.right})
    public void onClick(View btn) {
        switch (btn.getId()) {
            case R.id.left: {
                rotateView(view, new CycleInterpolator(4));
                break;
            }
            case R.id.right: {
                rotateView(view, android.support.v4.view.animation.PathInterpolatorCompat.create(0.5f, 0, 0.5f, 1));
                break;
            }
        }
    }

    private void rotateView(View view, Interpolator interpolator) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(interpolator);
        rotateAnimation.setDuration(2000);
        view.startAnimation(rotateAnimation);

    }
}
