package com.ssynhtn.helloworld;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by huangtongnao on 2018/4/11.
 */

public class Glyph {
    private static final String TAG = Glyph.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.M)
    public String work() {
        Paint paint = new Paint();
        paint.setTextSize(50);
        Rect rect = new Rect();

        int maxCodePoint = -1;
        String maxChar = null;
        float maxHeight = 0;
        int glyphCount = 0;

        for (int codePoint = Character.MIN_CODE_POINT; codePoint <= Character.MAX_CODE_POINT; codePoint++) {
            String str = new String(Character.toChars(codePoint));
            if (paint.hasGlyph(str)) {
                glyphCount++;
                paint.getTextBounds(str, 0, str.length(), rect);

                if (rect.height() > maxHeight) {
                    maxHeight = rect.height();
                    maxCodePoint = codePoint;
                    maxChar = str;
                }
            }
        }

        String res = "maxCodePoint " + maxCodePoint + ", maxChar " + maxChar + ", top " + maxHeight + ", glyph count " + glyphCount;
        Log.d(TAG, res);
        return maxChar;
    }

    void test() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.getTypeface();

        TextView textView = null;
//        Typeface.sSystemFontMap
    }

}
