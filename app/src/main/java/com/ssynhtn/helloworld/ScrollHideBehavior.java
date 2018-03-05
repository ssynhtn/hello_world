package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by huangtongnao on 2017/11/4.
 */

public class ScrollHideBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = ScrollHideBehavior.class.getSimpleName();

    public ScrollHideBehavior() {
    }

    public ScrollHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        int bottomMax = coordinatorLayout.getHeight() + child.getHeight();
        int bottomMin = coordinatorLayout.getHeight() - 200;
        float bottom = child.getBottom() + child.getTranslationY();
        float destBottom = bottom + dy;
        if (destBottom > bottomMax) {
            destBottom = bottomMax;
        } else if (destBottom < bottomMin) {
            destBottom = bottomMin;
        }

        float ratio = (bottomMax - destBottom) / (bottomMax - bottomMin);
        child.setAlpha(ratio);
        child.setScaleX(ratio);
        child.setScaleY(ratio);

        float offset = destBottom - bottom;
        float transY = (child.getTranslationY() + offset);
        child.setTranslationY(transY);
        Log.d(TAG, "dy " + dy + ", bottom " + bottom + ", destBottom " + destBottom + ", offset " + offset + ", botMax " + bottomMax + ", botMin " + bottomMin + ", currentTranslation " + transY);


    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
