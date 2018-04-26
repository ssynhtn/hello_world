package com.ssynhtn.helloworld;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangtongnao on 2017/8/8.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final String TAG = ZoomOutPageTransformer.class.getSimpleName();

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float DEST_GAP_DP = 21;   // dp
    private static final float DEST_GAP_PX = DEST_GAP_DP * 3;

    public static interface AlphaPolicy {
        boolean isUseAlpha(int pagePosition);
    }

    public static interface AlphaListener {
        void alphaChanged(int pagePosition, float alpha);
    }

    private AlphaPolicy mAlphaPolicy;
    private AlphaListener mAlphaListener;

    public ZoomOutPageTransformer() {

    }
    public ZoomOutPageTransformer(AlphaPolicy alphaPolicy) {
        mAlphaPolicy = alphaPolicy;
    }

    public void transformPage(View view, float position) {
        Log.d(TAG, "view: " + view + ", class: " + view.getClass().getSimpleName());
        PagerAdapter adapter = null;
//        int pageWidth = view.getWidth();
//        int pageHeight = view.getHeight();

//        RoundedImageView roundedImageView = findView(view);
//        Log.d(TAG, "imageView parent: " + roundedImageView.getParent().getClass().getName());
//        Log.d(TAG, "imageView padding: " + roundedImageView.getPaddingLeft());

        ViewPager viewPager = (ViewPager) view.getParent();
//        Log.d(TAG, "view: " + view + ", position: " + position + ", left " + view.getLeft() + ", scrollX: " + viewPager.getScrollX() + ", width: " + viewPager.getWidth() + ", measuredWidth: " + viewPager.getMeasuredWidth());
        float clientWidth = getClientWidth(viewPager);
        float dx = position * clientWidth - viewPager.getPaddingLeft();
//        Log.d(TAG, "view: " + view + ", dx: " + dx);
        float ratio = dx / clientWidth;
        transformPageRatio(clientWidth, view, ratio);

    }

    public static float getClientWidth(ViewPager viewPager) {
        return viewPager.getWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
    }

    public void transformPageRatio(float clientWidth, View view, float ratio) {
        float scale = 1 - Math.abs(ratio) * (1 - MIN_SCALE);
        view.setScaleX(scale);
        view.setScaleY(scale);

        float c = clientWidth * (1 - MIN_SCALE) / 2;
        float translationX = translationX(c, DEST_GAP_PX, ratio);
        view.setTranslationX(translationX);
        float translationY = view.getHeight() * (1 - scale) / 2;
        view.setTranslationY(translationY);

//        ViewGroup viewGroup = (ViewGroup) view.getParent();
//        int index = 0;
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            if (view == viewGroup.getChildAt(i)) {
//                index = i;
//                break;
//            }
//        }
//        Log.d(TAG, "index " + index);
//        logView(0, view);

        if (mAlphaPolicy != null) {
            int index = (int) view.getTag();

            boolean isUseAlpha = mAlphaPolicy.isUseAlpha(index);
            float alpha = 1;
            if (isUseAlpha) {
                alpha = 1 - Math.abs(ratio);
            }
            view.setAlpha(alpha);
            if (mAlphaListener != null) {
                mAlphaListener.alphaChanged(index, alpha);
            }
        }

//        if (view instanceof NoTouchFrameLayout) {
//            ((NoTouchFrameLayout) view).setDisableTouch(alpha < 0.5f);
//        }

        // Modify the default slide transition to shrink the page as well
//            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//            if (position < 0) {
//                view.setTranslationX(horzMargin - vertMargin / 2);
//            } else {
//                view.setTranslationX(-horzMargin + vertMargin / 2);
//            }

        // Scale the page down (between MIN_SCALE and 1)
//            view.setScaleX(scaleFactor);
//            view.setScaleY(scaleFactor);

        // Fade the page relative to its size.
//            view.setAlpha(MIN_ALPHA +
//                    (scaleFactor - MIN_SCALE) /
//                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));
    }


    private void logView(int prefix, View view) {
        Log.d(TAG, createPrefix(prefix) + view.getClass().getSimpleName());
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                logView(prefix + 2, group.getChildAt(i));
            }
        }
    }

    private String createPrefix(int prefix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prefix; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

//    private RoundedImageView findView(View view) {
//        if (view instanceof RoundedImageView) {
//            return (RoundedImageView) view;
//        }
//
//        if (view instanceof ViewGroup) {
//            ViewGroup group = (ViewGroup) view;
//            for (int i = 0; i < group.getChildCount(); i++) {
//                RoundedImageView roundedImageView = findView(group.getChildAt(i));
//                if (roundedImageView != null) {
//                    return roundedImageView;
//                }
//            }
//        }
//
//        return null;
//    }

    private float f(float x) {
        if (x == 0) return 0;
        if (x < 0) {
            return -f(-x);
        }

        int n = (int) Math.floor(x);
        return (n + 1) * (n + 1) * (x - n) + n * n * (n + 1 - x);
    }

    private float translationX(float c, float d, float x) {
        return -c * f(x) + d * x;
    }

    public void setAlphaListener(AlphaListener alphaListener) {
        mAlphaListener = alphaListener;
    }

}