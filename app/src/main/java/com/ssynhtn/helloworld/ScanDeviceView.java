package com.ssynhtn.helloworld;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtongnao on 2017/11/7.
 */

public class ScanDeviceView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = ScanDeviceView.class.getSimpleName();
    private Paint circlePaint;

    private ValueAnimator valueAnimator;
    private int lastValue;
    private List<ValueAnimator> animatorList = new ArrayList<>();

    private static final float SLOW_DOWN_FACTOR = 1.5f;
    private int fullDuration = 5000;
    private int duration = (int) (2000 * SLOW_DOWN_FACTOR);
    private int interval = (int) (750 * SLOW_DOWN_FACTOR);


    public ScanDeviceView(Context context) {
        this(context, null);
    }

    public ScanDeviceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        circlePaint.setColor(Color.WHITE);

        valueAnimator = ValueAnimator.ofInt(0, fullDuration);
        valueAnimator.setDuration(fullDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation == valueAnimator) {
            int currentValue = (int) valueAnimator.getAnimatedValue();
            if (currentValue >= lastValue + interval) {
                lastValue = currentValue;
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.setDuration(duration);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.addUpdateListener(this);
                animatorList.add(animator);
                animator.start();
            }

            Log.d(TAG, "update " + valueAnimator.getAnimatedValue());
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void startAnimation() {
        Log.d(TAG, "current animation list " + animatorList.size());
        valueAnimator.cancel();
        animatorList.clear();
        lastValue = Integer.MIN_VALUE;

        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Drawable icon = ContextCompat.getDrawable(getContext(), R.drawable.wave);
        float w = icon.getIntrinsicWidth();
        float h = icon.getIntrinsicHeight();
        icon.setBounds((int) (getWidth() / 2 - w / 2), (int) (getHeight() / 2 - h / 2), (int) (getWidth() / 2 + w / 2), (int) (getHeight() / 2 + h / 2));
        icon.draw(canvas);

        for (ValueAnimator animator : animatorList) {
            float progress = (float) animator.getAnimatedValue();
            float cx = getWidth() / 2;
            float cy = getHeight() / 2;
            float r = (float) (Math.sqrt(cx * cx + cy * cy) * progress + Math.sqrt(w * w + h * h) / 2);

            circlePaint.setStrokeWidth(getStrokeWidth() * (1 + progress));
            canvas.drawCircle(cx, cy, r, circlePaint);
        }

    }

    private float getStrokeWidth() {
        return 3;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                return true;
            }
            case MotionEvent.ACTION_UP: {
                startAnimation();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
