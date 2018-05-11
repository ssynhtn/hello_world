package com.ssynhtn.helloworld;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/28.
 */

public class AnimationDrawableView extends View {
    private AnimationDrawable drawable;



    public AnimationDrawableView(Context context) {
        this(context, null);
    }

    public AnimationDrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        drawable = (AnimationDrawable) ContextCompat.getDrawable(context, R.drawable.animtion_test_two);
        drawable.start();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }
}
