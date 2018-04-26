package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Flavien Laurent (flavienlaurent.com) on 23/08/13.
 */
public class DragLayout extends LinearLayout {

    private final ViewDragHelper mDragHelper;

    private View mDragView1;
    private View mDragView2;

    private boolean mDragEdge;
    private boolean mDragHorizontal;
    private boolean mDragCapture;
    private boolean mDragVertical;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView1 = findViewById(R.id.drag1);
        mDragView2 = findViewById(R.id.drag2);
    }

    public void setDragHorizontal(boolean dragHorizontal) {
        mDragHorizontal = dragHorizontal;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragVertical(boolean dragVertical) {
        mDragVertical = dragVertical;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragEdge(boolean dragEdge) {
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mDragEdge = dragEdge;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragCapture(boolean dragCapture) {
        mDragCapture = dragCapture;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mDragCapture) {
                return child == mDragView1;
            }
            return true;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            if (mDragEdge) {
                mDragHelper.captureChildView(mDragView1, pointerId);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (mDragVertical) {
                int left = child.getLeft();
                if (child.getLayoutParams() instanceof MarginLayoutParams) {
                    left -= ((MarginLayoutParams) child.getLayoutParams()).leftMargin;
                }

                int right = child.getRight();
                if (child.getLayoutParams() instanceof MarginLayoutParams) {
                    right += ((MarginLayoutParams) child.getLayoutParams()).rightMargin;
                }

                boolean isLeftRight = (left == getPaddingLeft() || right == getWidth() - getPaddingRight());

                if (isLeftRight) {
                    int topBound = getPaddingTop();
                    int bottomBound = getHeight() - getPaddingBottom() - mDragView1.getHeight();
                    if (mDragView1.getLayoutParams() instanceof MarginLayoutParams) {
                        topBound += ((MarginLayoutParams) mDragView1.getLayoutParams()).topMargin;
                        bottomBound -= ((MarginLayoutParams) mDragView1.getLayoutParams()).bottomMargin;
                    }

                    final int newTop = Math.min(Math.max(top, topBound), bottomBound);

                    return newTop;
                } else {
                    return child.getTop();
                }
            }
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mDragHorizontal || mDragCapture || mDragEdge) {
                int top = child.getTop();
                int bottom = child.getBottom();
                if (child.getLayoutParams() instanceof MarginLayoutParams) {
                    top -= ((MarginLayoutParams) child.getLayoutParams()).topMargin;
                    bottom += ((MarginLayoutParams) child.getLayoutParams()).bottomMargin;
                }

                boolean isTopBottom = (top == getPaddingTop() || bottom == getHeight() - getPaddingBottom());
                if (isTopBottom) {
                    int leftBound = getPaddingLeft();
                    int rightBound = getWidth() - getPaddingRight() - mDragView1.getWidth();

                    if (mDragView1.getLayoutParams() instanceof MarginLayoutParams) {
                        leftBound += ((MarginLayoutParams) mDragView1.getLayoutParams()).leftMargin;
                        rightBound -= ((MarginLayoutParams) mDragView1.getLayoutParams()).rightMargin;
                    }

                    final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                    return newLeft;
                } else {
                    return child.getLeft();
                }
            }
            return super.clampViewPositionHorizontal(child, left, dx);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

}
