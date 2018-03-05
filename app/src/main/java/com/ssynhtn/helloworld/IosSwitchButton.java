package com.ssynhtn.helloworld;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by huangtongnao on 2018/1/31.
 */

public class IosSwitchButton extends SwitchButton {
    public IosSwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public IosSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IosSwitchButton(Context context) {
        super(context);
        init(context);
    }

//    app:kswAnimationDuration="300"
//    app:kswBackDrawable="@drawable/ios_back_drawable"
//    app:kswThumbDrawable="@drawable/ios_thumb_selector"
//    app:kswThumbMarginBottom="-8dp"
//    app:kswThumbMarginLeft="-5dp"
//    app:kswThumbMarginRight="-5dp"
//    app:kswThumbMarginTop="-8dp"
//    app:kswThumbRangeRatio="2"
    private void init(Context context) {
        setAnimationDuration(300);
        setThumbDrawableRes(R.drawable.ios_thumb);
        setBackDrawableRes(R.drawable.ios_back_drawable);
        setThumbMargin(dpToPx(context, -5), dpToPx(context, -8), dpToPx(context, -5), dpToPx(context, -8));
        setThumbRangeRatio(1.67f);
    }

    public static float dpToPx(Context context, float dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
