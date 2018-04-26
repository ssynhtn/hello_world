package com.ssynhtn.helloworld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/17.
 */

public class GridItemSepDecoration extends RecyclerView.ItemDecoration {

    public static final int COLOR_NO_DRAW = 0;

    private int color;
    private int sep;

    private Rect rect;
    private Paint paint;

    public GridItemSepDecoration(int color, int sep) {
        this.color = color;
        this.sep = sep;

        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int position = gridLayoutManager.getPosition(view);
            int span = gridLayoutManager.getSpanCount();
            if (gridLayoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
                if (position < span) {
                    outRect.top = sep;
                } else {
                    outRect.top = 0;
                }

                if (position % span == 0) {
                    outRect.left = sep;
                } else {
                    outRect.left = 0;
                }

                outRect.right = outRect.bottom = sep;
            } else {
                if (position < span) {
                    outRect.left = sep;
                } else {
                    outRect.left = 0;
                }

                if (position % span == 0) {
                    outRect.top = sep;
                } else {
                    outRect.top = 0;
                }

                outRect.right = outRect.bottom = sep;
            }
        } else {
            outRect.setEmpty();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (color == COLOR_NO_DRAW) return;

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) return;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            layoutManager.getDecoratedBoundsWithMargins(child, rect);
            c.drawRect(rect.left, rect.top, rect.left + layoutManager.getLeftDecorationWidth(child), rect.bottom, paint);
            c.drawRect(rect.left + layoutManager.getLeftDecorationWidth(child), rect.top, rect.right - layoutManager.getRightDecorationWidth(child), rect.top + layoutManager.getTopDecorationHeight(child), paint);
            c.drawRect(rect.left + layoutManager.getLeftDecorationWidth(child), rect.bottom - layoutManager.getBottomDecorationHeight(child), rect.right - layoutManager.getRightDecorationWidth(child), rect.bottom, paint);
            c.drawRect(rect.right - layoutManager.getRightDecorationWidth(child), rect.top, rect.right, rect.bottom, paint);
        }
    }
}
