package com.ssynhtn.helloworld;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by huangtongnao on 2018/1/31.
 */

public class TouchButton extends View {

    public TouchButton(Context context) {
        super(context);
    }

    public TouchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                setBackgroundColor(Color.RED);
                break;
            }

//            case MotionEvent.ACTION_MOVE: return true;

            case MotionEvent.ACTION_UP: {
                setBackgroundColor(Color.BLUE);

                if (isInside(event.getX(), event.getY())) {
                    Toast.makeText(getContext(), "onClick", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

        return true;
    }

    private boolean isInside(float x, float y) {
        return x >= 0 && x <= getWidth() && y >= 0 && y < getHeight();
    }
}
