package com.langgan.haoshuimian.view.titlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.langgan.haoshuimian.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by huangtongnao on 2018/3/26.
 * 这个ViewGroup有点类似于LinearLayout, 默认对于child view它会从左向右排列和FrameLayout
 */

public class MyTitleBarViewCustom extends ViewGroup {
    private static final String TAG = MyTitleBarViewCustom.class.getSimpleName();

    public MyTitleBarViewCustom(Context context) {
        this(context, null);
    }

    public MyTitleBarViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private List<View> centerViews = new ArrayList<>();
    private List<View> rightViews = new ArrayList<>();

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        centerViews.clear();
        rightViews.clear();

        int leftWidthUsed = 0;
        int rightWidthUsed = 0;
        int heightUsed = 0;
        int widthUsed = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int gravity = params.gravity;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gravity = Gravity.getAbsoluteGravity(gravity, getLayoutDirection());
            }
            int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;

            if (horizontalGravity == Gravity.CENTER_HORIZONTAL) {
                centerViews.add(child);
                Log.d(TAG, "meet center view " + child);
                continue;
            }

            if (horizontalGravity == Gravity.RIGHT) {
                rightViews.add(child);
                continue;
            }

            // gravity为left或者没有指定的都叠在左边
            measureChildWithMargins(child, widthMeasureSpec, leftWidthUsed + rightWidthUsed, heightMeasureSpec, 0);
            params.left = leftWidthUsed;
            leftWidthUsed += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            heightUsed = Math.max(heightUsed, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);

            Log.d(TAG, "left view " + child + ", left " + params.left);
        }

        Collections.reverse(rightViews);
        for (View child : rightViews) {
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            measureChildWithMargins(child, widthMeasureSpec, leftWidthUsed + rightWidthUsed, heightMeasureSpec, 0);
            params.right = rightWidthUsed;
            rightWidthUsed += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            heightUsed = Math.max(heightUsed, child.getMeasuredHeight() + params.topMargin + params.bottomMargin);

            Log.d(TAG, "right view " + child + ", right " + params.left);
        }

        if (!centerViews.isEmpty()) {
            widthUsed = Math.max(leftWidthUsed, rightWidthUsed) * 2;
            int centerWidthUsed = 0;
            for (View centerView : centerViews) {
                measureChildWithMargins(centerView, widthMeasureSpec, widthUsed, heightMeasureSpec, 0);

                LayoutParams params = (LayoutParams) centerView.getLayoutParams();
                heightUsed = Math.max(heightUsed, centerView.getMeasuredHeight() + params.topMargin + params.bottomMargin);
                centerWidthUsed = Math.max(centerWidthUsed, centerView.getMeasuredWidth() + params.leftMargin + params.rightMargin);
            }
            widthUsed += centerWidthUsed;
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
            if (child.getVisibility() == GONE) continue;

            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int gravity = params.gravity;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                gravity = Gravity.getAbsoluteGravity(params.gravity, getLayoutDirection());
            }
            int left = 0;
            switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
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
            switch (gravity & Gravity.VERTICAL_GRAVITY_MASK) {
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

            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.MyTitleBarViewCustom_Layout);
            gravity = typedArray.getInt(R.styleable.MyTitleBarViewCustom_Layout_android_layout_gravity, GRAVITY_NONE);
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
