package com.ssynhtn.helloworld.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangtongnao on 2018/4/9.
 */

public class ShaderView extends View {
    private Paint paint;
    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#80000000"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        paint.setShader(new LinearGradient(0, 0, 0, h, Color.RED, Color.BLUE, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

    }
}
