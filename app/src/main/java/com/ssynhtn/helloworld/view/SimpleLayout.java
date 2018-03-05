package com.ssynhtn.helloworld.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangtongnao on 2018/1/12.
 */

public class SimpleLayout extends ViewGroup {
    private View imageView;
    private View titleView;
    private View subTitleView;

    public SimpleLayout(Context context) {
        this(context, null);
    }

    public SimpleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        imageView = getChildAt(0);
        titleView = getChildAt(1);
        subTitleView = getChildAt(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) imageView.getLayoutParams();
        int widthUsed = getPaddingLeft() + marginLayoutParams.leftMargin;
        int heightUsed = getPaddingTop() + marginLayoutParams.topMargin;
        measureChildWithMargins(imageView, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
        widthUsed += imageView.getMeasuredWidth() + marginLayoutParams.rightMargin;

        MarginLayoutParams marginLayoutParams2 = (MarginLayoutParams) titleView.getLayoutParams();
        heightUsed = getPaddingTop() + marginLayoutParams2.topMargin;
        measureChildWithMargins(titleView, widthMeasureSpec, widthUsed + marginLayoutParams2.leftMargin, heightMeasureSpec, heightUsed);
        heightUsed += titleView.getMeasuredHeight() + marginLayoutParams2.bottomMargin;

        MarginLayoutParams marginLayoutParams3 = (MarginLayoutParams) subTitleView.getLayoutParams();
        heightUsed += marginLayoutParams3.topMargin;
        measureChildWithMargins(subTitleView, widthMeasureSpec, widthUsed + marginLayoutParams3.leftMargin, heightMeasureSpec, heightUsed);

        int width = getPaddingLeft() + getPaddingRight() + imageView.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + Math.max(titleView.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin,
                subTitleView.getMeasuredWidth() + marginLayoutParams3.leftMargin + marginLayoutParams3.rightMargin);
        int height = getPaddingTop() + getPaddingBottom() + Math.max(imageView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, titleView.getMeasuredHeight() + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin + subTitleView.getMeasuredHeight() + marginLayoutParams3.topMargin + marginLayoutParams3.bottomMargin);

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams params = (MarginLayoutParams) imageView.getLayoutParams();
        int x = getPaddingLeft() + params.leftMargin;
        int y = getPaddingTop() + params.topMargin;
        imageView.layout(x,y, x + imageView.getMeasuredWidth(), y + imageView.getMeasuredHeight());
        x += imageView.getMeasuredWidth() + params.rightMargin;

        params = (MarginLayoutParams) titleView.getLayoutParams();
        x += params.leftMargin;
        y = getPaddingTop() + params.topMargin;
        titleView.layout(x, y, x + titleView.getMeasuredWidth(), y + titleView.getMeasuredHeight());

        x -= params.leftMargin;
        y += titleView.getMeasuredHeight();
        params = (MarginLayoutParams) subTitleView.getLayoutParams();
        x += params.leftMargin;
        y += params.topMargin;
        subTitleView.layout(x, y, x + subTitleView.getMeasuredWidth(), y + subTitleView.getMeasuredHeight());

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p.width, p.height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
