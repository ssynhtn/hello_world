package com.ssynhtn.helloworld.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom view that shows a pie chart and, optionally, a label.
 */
public class PieChart extends ViewGroup {
    private static final String TAG = PieChart.class.getSimpleName();

    private List<Item> mData = new ArrayList<>();
    private float mTotal = 0.0f;

    private RectF mPieBounds = new RectF();
    private Paint mPiePaint;

    private int mPieRotation;

    private PieView mPieView;

    private OverScroller mScroller;
    private GestureDetector mDetector;

    /**
     * The initial fling velocity is divided by this amount.
     */
    public static final int FLING_VELOCITY_DOWNSCALE = 4;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float diameter = Math.min(r - l, b - t);
        mPieBounds.set(0.0f, 0.0f, diameter, diameter);
        mPieView.layout((int) mPieBounds.left, (int) mPieBounds.top, (int) mPieBounds.right, (int) mPieBounds.bottom);
        mPieView.setPivotX(mPieBounds.width() / 2);
        mPieView.setPivotY(mPieBounds.height() / 2);
    }

    public void addItem(float value, int color) {
        Item it = new Item();
        it.mColor = color;
        it.mValue = value;

        mTotal += value;

        mData.add(it);

        int currentAngle = 0;
        for (Item item : mData) {
            item.mStartAngle = currentAngle;
            item.mEndAngle = (int) ((float) currentAngle + item.mValue * 360.0f / mTotal);
            currentAngle = item.mEndAngle;
        }

        invalidate();
    }

    /**
     * Set the current rotation of the pie graphic. Setting this value may change
     * the current item.
     *
     * @param rotation The current pie rotation, in degrees.
     */
    public void setPieRotation(int rotation) {
        rotation = (rotation % 360 + 360) % 360;
        mPieRotation = rotation;
        mPieView.setRotation(rotation);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the GestureDetector interpret this event
        boolean handled = mDetector.onTouchEvent(event);

        // If the GestureDetector doesn't want this event, do some custom processing.
        // This code just tries to detect when the user is done scrolling by looking
        // for ACTION_UP events.
        if (!handled) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // User is done scrolling, it's now safe to do things like autocenter
                mScroller.forceFinished(true);
                handled = true;
            }
        }
        return handled;
    }

    /**
     * Initialize the control. This code is in a separate method so that it can be
     * called from both constructors.
     */
    private void init() {
        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);

        mPieView = new PieView(getContext());
        addView(mPieView);


        // Create a Scroller to handle the fling gesture.
        mScroller = new OverScroller(getContext());

        // Create a gesture detector to handle onTouch messages
        mDetector = new GestureDetector(getContext(), new GestureListener());

        // Turn off long press--this control doesn't use it, and if long press is enabled,
        // you can't scroll for a bit, pause, then scroll some more (the pause is interpreted
        // as a long press, apparently)
        mDetector.setIsLongpressEnabled(false);
    }

    private int computeScrollCount;
    @Override
    public void computeScroll() {
        super.computeScroll();

        computeScrollCount++;

        Log.d(TAG, "computeScroll, count " + computeScrollCount);

        if (!mScroller.isFinished()) {
            mScroller.computeScrollOffset();
            setPieRotation(mScroller.getCurrY());
        }
    }



    private class PieView extends View {
        public PieView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (Item it : mData) {
                mPiePaint.setColor(it.mColor);
                canvas.drawArc(mBounds,
                        360 - it.mEndAngle,
                        it.mEndAngle - it.mStartAngle,
                        true, mPiePaint);
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mBounds = new RectF(0, 0, w, h);
        }

        RectF mBounds;
    }

    /**
     * Maintains the state for a data item.
     */
    private class Item {
        public float mValue;
        public int mColor;

        // computed values
        public int mStartAngle;
        public int mEndAngle;
    }

    /**
     * Extends {@link GestureDetector.SimpleOnGestureListener} to provide custom gesture
     * processing.
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Set the pie rotation directly.
            float scrollTheta = vectorToScalarScroll(
                    distanceX,
                    distanceY,
                    e2.getX() - mPieBounds.centerX(),
                    e2.getY() - mPieBounds.centerY());
            setPieRotation(mPieRotation - (int) scrollTheta / FLING_VELOCITY_DOWNSCALE);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Set up the Scroller for a fling
            float scrollTheta = vectorToScalarScroll(
                    velocityX,
                    velocityY,
                    e2.getX() - mPieBounds.centerX(),
                    e2.getY() - mPieBounds.centerY());
            mScroller.fling(
                    0,
                    mPieRotation,
                    0,
                    (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            return true;
        }
    }


    /**
     * Helper method for translating (x,y) scroll vectors into scalar rotation of the pie.
     *
     * @param dx The x component of the current scroll vector.
     * @param dy The y component of the current scroll vector.
     * @param x  The x position of the current touch, relative to the pie center.
     * @param y  The y position of the current touch, relative to the pie center.
     * @return The scalar representing the change in angular position for this scroll.
     */
    private static float vectorToScalarScroll(float dx, float dy, float x, float y) {
        // get the length of the vector
        float l = (float) Math.sqrt(dx * dx + dy * dy);

        // decide if the scalar should be negative or positive by finding
        // the dot product of the vector perpendicular to (x,y). 
        float crossX = -y;
        float crossY = x;

        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);

        return l * sign;
    }
}