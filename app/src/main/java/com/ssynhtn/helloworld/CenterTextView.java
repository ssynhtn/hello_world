package com.ssynhtn.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/12.
 */

public class CenterTextView extends View {
    private Paint paint;
    private Rect rect;

    public CenterTextView(Context context) {
        this(context, null);
    }

    public CenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.my50sp));
        paint.setColor(Color.BLACK);

        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String text = new String(Character.toChars(Integer.parseInt("1d11f", 16)));
        paint.getTextBounds(text, 0, text.length(), rect);
        float baseX = getWidth() / 2.0f - rect.exactCenterX();
        float baseY = getHeight() / 2.0f - rect.exactCenterY();
        canvas.drawText(text, baseX, baseY, paint);
    }
}
