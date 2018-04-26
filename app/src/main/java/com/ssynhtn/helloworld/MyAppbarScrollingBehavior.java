package com.ssynhtn.helloworld;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/9.
 */

public class MyAppbarScrollingBehavior extends AppBarLayout.ScrollingViewBehavior {

    public MyAppbarScrollingBehavior() {
    }

    public MyAppbarScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency) && parent.getChildAt(0) == dependency;
    }
}
