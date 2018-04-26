package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by huangtongnao on 2018/4/25.
 */

public class PointerView extends View {

    private static final String TAG = PointerView.class.getSimpleName();

    public PointerView(Context context) {
        super(context);
    }

    public PointerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(event.getActionIndex());
        String prefix = null;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                prefix = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                prefix = "ACTION_POINTER_DOWN";
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                prefix = "ACTION_POINTER_UP";
                break;
            }
            case MotionEvent.ACTION_UP: {
                prefix = "ACTION_UP";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                StringBuilder sb = new StringBuilder();
                sb.append("ACTION_MOVE");
                Map<Integer, Float> xMap = new TreeMap<>();
                Map<Integer, Float> yMap = new TreeMap<>();
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id = event.getPointerId(i);
                    xMap.put(id, event.getX(i));
                    yMap.put(id, event.getY(i));
                }

                for (int id : xMap.keySet()) {
                    sb.append(String.format(Locale.ENGLISH, " id %d x %.0f, y %.0f", id, xMap.get(id), yMap.get(id)));
                }
                prefix = sb.toString();
                break;
            }
        }

        if (prefix != null) {
            if (prefix.startsWith("ACTION_MOVE")) {
                Log.v(TAG, prefix);
            } else {
                Log.d(TAG, prefix + " pointerId " + pointerId + ", pointerIndex " + pointerIndex);
            }
        }

        return true;
    }
}
