package com.ssynhtn.helloworld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/13.
 */

public class SepItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;
    private Rect rect;

    public SepItemDecoration() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION || position == 0) {
            outRect.set(0, 0, 0, 0);
        } else {
            int sep = view.getResources().getDimensionPixelSize(R.dimen.sep_height);
            outRect.set(0, sep, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) return;

        c.save();
        int left, right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right, parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, rect);
            float top = rect.top + child.getTranslationY();
            float bottom = top + layoutManager.getTopDecorationHeight(child);
            paint.setColor(Color.RED);
            c.drawRect(left, top, rect.centerX(), bottom, paint);
            paint.setColor(Color.GREEN);
            c.drawRect(rect.centerX(), top, right, bottom, paint);
        }

        c.restore();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int sep = parent.getResources().getDimensionPixelSize(R.dimen.sep_height);
//        paint.setColor(Color.GREEN);
//        c.drawRect(0, sep, parent.getWidth(), sep * 2, paint);
    }
}
