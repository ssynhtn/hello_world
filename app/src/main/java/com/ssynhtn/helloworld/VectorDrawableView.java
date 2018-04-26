package com.ssynhtn.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangtongnao on 2018/4/18.
 */

public class VectorDrawableView extends View {

    private static final String TAG = VectorDrawableView.class.getSimpleName();

    private VectorDrawableCompat drawable;

    public VectorDrawableView(Context context) {
        this(context, null);
    }

    public VectorDrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_circle, context.getTheme());
        Log.d(TAG, "intrinsic width " + drawable.getIntrinsicWidth() + ", height " + drawable.getIntrinsicHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ViewGroup parent = (ViewGroup) getParent();
        int index = -1;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i) == this) {
                index = i;
                break;
            }
        }

        if (index % 2 == 0) {
            drawable.setTint(Color.RED);
        } else {
            drawable.setTint(Color.BLUE);
        }
        drawable.setBounds(getWidth() / 4, getHeight() / 4, getWidth() * 3 / 4, getHeight() * 3 / 4);
        drawable.draw(canvas);

    }
}
