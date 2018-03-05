package com.ssynhtn.helloworld;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by huangtongnao on 2017/11/8.
 */

public class StepCircleView extends View {
    private int targetSteps;
    private int steps;
    private int distance;
    private int calories;
    private int stepsTwo;
    private int distanceTwo;
    private int caloriesTwo;

    private float ringWidth = dpToPx(12);
    private float ringMargin = dpToPx(10);
    private float innerRingWidth = dpToPx(2);
    private float markerRadius = dpToPx(3);
    private float innerRingAlpha = 0.5f;
    private float textAlpha = 0.5f;
    private float textSizeLarge = dpToPx(58);
    private float textSizeSmall = dpToPx(13);
    private float sepWidth = dpToPx(1);
    private float textLineSpacing = dpToPx(14);
    private float textWordSpacing = dpToPx(6);

    private Paint paint;

    private TextPaint textPaint;
    private Path path;
    private PathEffect pathEffect;

    private RectF rectF = new RectF();
    private Rect rect = new Rect();

    private Drawable icon;

    private ValueAnimator ringAnimator;
    private float animatedValue;

    public StepCircleView(Context context) {
        this(context, null);
    }

    public StepCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        icon = ContextCompat.getDrawable(context, R.drawable.wave);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);

        path = new Path();

        ringAnimator = ValueAnimator.ofFloat(0, 1);
        ringAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                ViewCompat.postInvalidateOnAnimation(StepCircleView.this);
            }
        });
        ringAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatedValue = 1.0f;
                steps = stepsTwo;
                distance = distanceTwo;
                calories = caloriesTwo;
                ViewCompat.postInvalidateOnAnimation(StepCircleView.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                animatedValue = 1.0f;
                steps = stepsTwo;
                distance = distanceTwo;
                calories = caloriesTwo;
                ViewCompat.postInvalidateOnAnimation(StepCircleView.this);
            }
        });
    }

    public void setTargetSteps(int targetSteps) {
        this.targetSteps = targetSteps;
        invalidate();
    }

    public void setSportsData(int steps, int distance, int calories) {
        this.stepsTwo = steps;
        this.distanceTwo = distance;
        this.caloriesTwo = calories;

        startRingAnimation();
    }

    private void startRingAnimation() {
        ringAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float bigR = Math.min(w, h) / 2;
        float innerR = bigR - ringWidth - ringMargin - innerRingWidth / 2;
        float length = (float) (Math.PI * 2 * innerR);
        int count = (int) (length / 9);
        float len = length / count;
        pathEffect = new DashPathEffect(new float[]{len / 3, len * 2 / 3}, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();
        float cx = w / 2;
        float cy = h / 2;
        float bigR = Math.min(w, h) / 2;

        float r = bigR - ringWidth / 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setAlpha(255);
        paint.setPathEffect(null);
        canvas.drawCircle(cx, cy, r, paint);

        float innerR = bigR - ringWidth - ringMargin - innerRingWidth / 2;
        path.reset();
        path.addCircle(cx, cy, innerR, Path.Direction.CCW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(pathEffect);
        paint.setStrokeWidth(innerRingWidth);
        paint.setAlpha((int) (255 * innerRingAlpha));
        canvas.drawPath(path, paint);

        rectF.set(cx - innerR, cy - innerR, cx + innerR, cy + innerR);
        int animatedSteps = getAnimatedNumber(steps, stepsTwo);
        float sweep = 0;
        if (animatedSteps >= targetSteps) {
            sweep = 360;
        } else {
            sweep = animatedSteps * 1.0f / targetSteps * 360;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(innerRingWidth);
        paint.setPathEffect(null);
        paint.setAlpha(255);
        canvas.drawArc(rectF, -90, sweep, false, paint);

        if (sweep < 360) {
            canvas.save();
            canvas.rotate(sweep, cx, cy);
            paint.setStyle(Paint.Style.FILL);
            paint.setPathEffect(null);
            paint.setAlpha(255);
            canvas.drawCircle(cx, cy - innerR, markerRadius, paint);
            canvas.restore();
        }


        String text = Integer.toString(animatedSteps);
        textPaint.setTextSize(textSizeLarge);
        textPaint.setAlpha(255);
        textPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextCenteredAt(canvas, text, cx, cy, rect, textPaint);

        float halfTextHeight = rect.height() / 2;
        String temp = "0123456789米卡路里";
        textPaint.setTextSize(textSizeSmall);
        textPaint.getTextBounds(temp, 0, temp.length(), rect);
        float cyText = cy + halfTextHeight + textLineSpacing + rect.height() / 2;

        paint.setAlpha((int) (255 * innerRingAlpha));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(sepWidth);
        paint.setPathEffect(null);
        canvas.drawLine(cx, cyText - rect.height() / 2, cx, cyText + rect.height() / 2, paint);

        String smallText = String.format("%d米", getAnimatedNumber(distance, distanceTwo));
        textPaint.setAlpha((int) (255 * textAlpha));
        textPaint.getTextBounds(smallText, 0, smallText.length(), rect);
        drawTextCenteredAt(canvas, smallText, cx - textWordSpacing - rect.width() / 2, cyText, rect, textPaint);

        smallText = String.format("%d卡路里", getAnimatedNumber(calories, caloriesTwo));
        textPaint.getTextBounds(smallText, 0, smallText.length(), rect);
        drawTextCenteredAt(canvas, smallText, cx + textWordSpacing + rect.width() / 2, cyText, rect, textPaint);

        float textBottom = cyText + rect.height() / 2;
        float innerCircleBottom = cy + innerR;
        float iconH = (innerCircleBottom - textBottom) / 3;
        if (iconH > 0) {
            float iconW = icon.getIntrinsicWidth() * iconH / icon.getIntrinsicHeight();
            float iconCy = (textBottom + innerCircleBottom) / 2;
            icon.setBounds((int) (cx - iconW / 2), (int) (iconCy - iconH / 2), (int) (cx + iconW / 2), (int) (iconCy + iconH / 2));
            icon.draw(canvas);
        }



    }

    private int getAnimatedNumber(int first, int second) {
        return (int) (first * (1 - animatedValue) + second * animatedValue);
    }

    private void drawTextCenteredAt(Canvas canvas, String text, float cx, float cy, Rect textBounds, TextPaint textPaint) {
        float rx = cx - (textBounds.left + textBounds.right) / 2;
        float ry = cy - (textBounds.top + textBounds.bottom) / 2;
        canvas.drawText(text, rx, ry, textPaint);
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

}
