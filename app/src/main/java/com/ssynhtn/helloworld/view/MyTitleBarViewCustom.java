package com.ssynhtn.helloworld.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.ssynhtn.helloworld.R;

/**
 * Created by huangtongnao on 2018/3/26.
 */

public class MyTitleBarViewTwo extends ViewGroup {
    private static final String TAG = MyTitleBarViewTwo.class.getSimpleName();

    public MyTitleBarViewTwo(Context context) {
        this(context, null);
    }

    public MyTitleBarViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View centerView = null;

        int leftWidthUsed = 0;
        int rightWidthUsed = 0;
        int heightUsed = 0;
        int widthUsed = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int gravity = Gravity.getAbsoluteGravity(params.gravity, getLayoutDirection()) & Gravity.HORIZONTAL_GRAVITY_MASK;

            if (gravity == Gravity.CENTER_HORIZONTAL) {
                centerView = child;
                Log.d(TAG, "meet center view " + child);
                continue;
            }

            if (gravity == Gravity.LEFT) {
                // left
                measureChildWithMargins(child, widthMeasureSpec, leftWidthUsed + rightWidthUsed, heightMeasureSpec, 0);
                params.left = leftWidthUsed;
                leftWidthUsed += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                Log.d(TAG, "left view " + child + ", left " + params.left);
            } else if (gravity == Gravity.RIGHT) {
                // right
                measureChildWithMargins(child, widthMeasureSpec, leftWidthUsed + rightWidthUsed, heightMeasureSpec, 0);
                params.right = rightWidthUsed;
                rightWidthUsed += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;

                Log.d(TAG, "right view " + child + ", right " + params.left);
            }

            heightUsed = Math.max(heightUsed, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);
        }

        if (centerView != null) {
            widthUsed = Math.max(leftWidthUsed, rightWidthUsed) * 2;

            measureChildWithMargins(centerView, widthMeasureSpec, widthUsed, heightMeasureSpec, 0);

            LayoutParams params = (LayoutParams) centerView.getLayoutParams();
            heightUsed = Math.max(heightUsed, centerView.getMeasuredHeight() + params.topMargin + params.bottomMargin);
        } else {
            widthUsed = leftWidthUsed + rightWidthUsed;
        }

        setMeasuredDimension(resolveSize(widthUsed, widthMeasureSpec), resolveSize(heightUsed, heightMeasureSpec));

    }

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int gravity = Gravity.getAbsoluteGravity(params.gravity, getLayoutDirection()) & Gravity.HORIZONTAL_GRAVITY_MASK;
            int left = 0;
            switch (gravity) {
                case Gravity.LEFT: {
                    left = getPaddingLeft() + params.left + params.leftMargin;
                    break;
                }
                case Gravity.RIGHT: {
                    left = r - l - getPaddingRight() - params.right - params.rightMargin - child.getMeasuredWidth();
                    break;
                }
                case Gravity.CENTER_HORIZONTAL: {
                    left = (r - l - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft() - (child.getMeasuredWidth() + params.leftMargin + params.rightMargin) / 2 + params.leftMargin;
                    break;
                }
            }

            int top = 0;
            int verticalGravity = params.gravity & Gravity.VERTICAL_GRAVITY_MASK;
            switch (verticalGravity) {
                case Gravity.TOP: {
                    top = getPaddingTop() + params.topMargin;
                    break;
                }
                case Gravity.BOTTOM: {
                    top = b - t - getPaddingBottom() - child.getMeasuredHeight() - params.bottomMargin;
                    break;
                }
                case Gravity.CENTER_VERTICAL: {
                    top = (b - t - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop() - (child.getMeasuredHeight() + params.topMargin + params.bottomMargin) / 2 + params.topMargin;
                }
            }

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams) {
            LayoutParams lp = (LayoutParams) p;
            return new LayoutParams(lp);
        } else if (p instanceof MarginLayoutParams) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) p;
            return new LayoutParams(marginLayoutParams);
        } else {
            return new LayoutParams(p);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    public static class LayoutParams extends MarginLayoutParams {

        private static final int GRAVITY_NONE = -1;
        public int gravity = GRAVITY_NONE;

        public int left;
        public int right;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.MyTitleBarViewTwo_Layout);
            gravity = typedArray.getInt(R.styleable.MyTitleBarViewTwo_Layout_android_layout_gravity, GRAVITY_NONE);
            typedArray.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.gravity = source.gravity;
        }
    }
}
