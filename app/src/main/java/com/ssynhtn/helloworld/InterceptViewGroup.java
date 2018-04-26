package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by huangtongnao on 2018/4/25.
 */

public class InterceptViewGroup extends FrameLayout {
    private static final String TAG = InterceptViewGroup.class.getSimpleName();

    public InterceptViewGroup(@NonNull Context context) {
        super(context);
    }

    public InterceptViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int dispatchCount;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dispatchCount++;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "not intercept down");
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "intercept move");
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int onTouchCount;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onTouchCount++;
        Log.d(TAG, "dis count " + dispatchCount + ", on count " + onTouchCount);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "onTouch down");
                Log.d(TAG, "stack trace ", new RuntimeException());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "onTouch move");
                break;
            }
            default: {
                Log.d(TAG, "other events");
                break;
            }
        }

        return true;
    }
}
