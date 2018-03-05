package com.ssynhtn.helloworld;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends AppCompatActivity {
    private static final String TAG = AnimatorActivity.class.getSimpleName();
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        ButterKnife.bind(this);

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.d(TAG, "value " + valueAnimator.getAnimatedValue());
            }
        });
    }

    @OnClick({R.id.btn_start, R.id.btn_end, R.id.btn_canel})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_start: {
                animator.start();
                break;
            }
            case R.id.btn_end: {
                animator.end();
                break;
            }
            case R.id.btn_canel: {
                animator.cancel();
                break;
            }
            default: break;
        }
    }
}
