package com.ssynhtn.helloworld.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangtongnao on 2018/3/30.
 */

public class TestView extends View {

    private Paint paint;
    private Path path;


    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private float angle;

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 90).setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = (float) animation.getAnimatedValue();
                ViewCompat.postInvalidateOnAnimation(TestView.this);
            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cx = w / 2;
        int cy = h / 2;
        int radius = Math.min(w, h) / 2;
        int[] colors = {Color.RED, Color.BLUE, Color.CYAN};
        float[] stops = {0, 0.8f, 1};
        RadialGradient gradient = new RadialGradient(cx, cy, radius, colors, stops, Shader.TileMode.MIRROR);

        paint.setShader(gradient);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        setPath();

        canvas.drawPath(path, paint);


    }

    private void setPath() {
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2;
        int left = cx - radius;
        int right = cx + radius;
        int top = cy - radius;
        int bottom = cy + radius;
        int stripeWidth = 80;
        path.reset();
//        path.moveTo(0, cy);
        path.arcTo(left, top, right, bottom, 180, angle, true);
//        path.lineTo(cx, top + stripeWidth);
        path.arcTo(left + stripeWidth, top + stripeWidth, getWidth() - stripeWidth, bottom - stripeWidth, 180 + angle, -angle, false);
        path.close();
    }
}
