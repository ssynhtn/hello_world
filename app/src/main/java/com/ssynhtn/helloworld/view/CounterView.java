package com.ssynhtn.helloworld.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by huangtongnao on 2018/1/12.
 */

public class CounterView extends View {
    private static final String MAX_STRING = "9999";
    private static final String TAG = CounterView.class.getSimpleName();

    private Paint paint;
    private Paint numPaint;

    private Rect rect;

    private String countString;
    private int count;

    public CounterView(Context context) {
        this(context, null);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);

        numPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        numPaint.setColor(Color.RED);
        numPaint.setTextSize(30 * getResources().getDisplayMetrics().scaledDensity);

        rect = new Rect();

        setCount(1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (numPaint.measureText(MAX_STRING) + getPaddingLeft() + getPaddingRight());
        Paint.FontMetrics fontMetrics = numPaint.getFontMetrics();
        int desiredHeight = (int) ((fontMetrics.descent - fontMetrics.ascent) * 2 + getPaddingTop() + getPaddingBottom());

        Log.d(TAG, "desired width " + desiredHeight + ", height " + desiredHeight + ", paddingLR " + getPaddingLeft() + ", " + getPaddingRight());
        setMeasuredDimension(resolveSize(desiredWidth, widthMeasureSpec), resolveSize(desiredHeight, heightMeasureSpec));
    }

    private void setCount(int count) {
        this.count = count;
        this.countString = "" + count;

        invalidate();
    }

    public void increment() {
        setCount(count + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), canvas.getWidth() - getPaddingRight(), canvas.getHeight() - getPaddingBottom(), paint);

        float width = canvas.getWidth();
        float height = canvas.getHeight();

        Paint.FontMetrics fontMetrics = numPaint.getFontMetrics();
        float cy = (height - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();
        float cx = (width - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        float baseLineY = cy - (fontMetrics.descent + fontMetrics.ascent) / 2;

        numPaint.getTextBounds(countString, 0, countString.length(), rect);
        float baseLineY2 = cy - (rect.top + rect.bottom) / 2;

        float textWidth = numPaint.measureText(countString);

        canvas.drawText(countString, cx - textWidth / 2, baseLineY, numPaint);
        numPaint.setColor(Color.parseColor("#8000ffff"));
        canvas.drawText(countString, cx - textWidth / 2, baseLineY2, numPaint);

        Log.d(TAG, "baseLineY " + baseLineY + ", 2: " + baseLineY2);
    }
}
