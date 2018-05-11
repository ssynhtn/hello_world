package com.ssynhtn.helloworld;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by huangtongnao on 2018/4/26.
 */

public class TranslationPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pos = getPosition(page);

        if (pos == 0) {
            page.setTranslationX(page.getWidth() * (-position));
        }
    }

    private int getPosition(View page) {
        ViewPager.LayoutParams params = (ViewPager.LayoutParams) page.getLayoutParams();
        try {
            Field field = ViewPager.LayoutParams.class.getDeclaredField("position");
            field.setAccessible(true);
            return field.getInt(params);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
