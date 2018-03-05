package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by huangtongnao on 2018/1/31.
 */

public class TouchScrollView extends FrameLayout {
    private float mPreviousY;
    public TouchScrollView(Context context) {
        super(context);
    }

    public TouchScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mPreviousY = event.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float dist = mPreviousY - event.getY();
                scrollBy(0, (int) dist);
                mPreviousY = event.getY();
                break;
            }
        }

        return true;
    }
}
