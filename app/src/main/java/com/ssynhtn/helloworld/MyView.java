package com.ssynhtn.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by huangtongnao on 2018/3/9.
 */

public class MyView extends View {

    private static final String TAG = MyView.class.getSimpleName();
    private Paint maskPaint;
    private Paint normalPaint;
    Bitmap mask;
    Bitmap canvasBitmap;
    Canvas c;
    Rect src = new Rect();
    Rect dest = new Rect();

    Bitmap maskBitmap;
    Canvas maskCanvas = new Canvas();
    Paint tempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask_doctor);
        c = new Canvas();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        c.setBitmap(canvasBitmap);

//        maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gradient);

        maskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        maskCanvas.setBitmap(maskBitmap);

//        ShapeDrawable shapeDrawable = new ShapeDrawable();
//        RadialGradient gradient = new RadialGradient(w / 2, h / 2, Math.min(w, h) / 2, new int[]{Color.parseColor("#00000000"), Color.parseColor("#ff000000")}, null, Shader.TileMode.MIRROR);

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(maskCanvas);
//
//
//        int alpha = 255 / 4;
//        for (int i = 0; i < 4; i++) {
//            tempPaint.setColor(Color.argb(i * alpha, 0, 0, 0));
//            maskCanvas.drawRect(0, 0, w, h / 4 * i, tempPaint);
//        }


        Log.d(TAG, "mask size " + maskBitmap.getWidth() + " " + maskBitmap.getHeight() + ", w " + w + ", h " + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        c.drawColor(ContextCompat.getColor(getContext(), R.color.trans_gray));

        src.set(0, 0, mask.getWidth(), mask.getHeight());
        dest.set(0, (c.getHeight() - c.getWidth()) / 2, c.getWidth(), (c.getHeight() + c.getWidth()) / 2);

        c.drawBitmap(mask, src, dest, maskPaint);
        canvas.drawBitmap(canvasBitmap, 0, 0, normalPaint);



    }
}
