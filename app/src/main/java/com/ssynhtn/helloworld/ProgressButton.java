package com.ssynhtn.helloworld;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by huangtongnao on 2017/11/10.
 */

public class ProgressButton extends LinearLayout {
    private String textNormal;
    private String textInProgress;
    private Drawable progressDrawable;

    private TextView textView;
    private ImageView imageView;

    ValueAnimator valueAnimator;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        textNormal = typedArray.getString(R.styleable.ProgressButton_pb_textNormal);
        textInProgress = typedArray.getString(R.styleable.ProgressButton_pb_textProgress);
        progressDrawable = typedArray.getDrawable(R.styleable.ProgressButton_pb_progressDrawable);
        typedArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.progress_button, this, true);
        textView = findViewById(R.id.text_view);
        imageView = findViewById(R.id.image_view);

        textView.setText(textNormal);
        imageView.setImageDrawable(progressDrawable);

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float) valueAnimator.getAnimatedValue();
                float degree = val * 360;
                imageView.setRotation(degree);
            }
        });
    }

    public void setInProgress(boolean inProgress, boolean buttonEnabled) {
        if (inProgress) buttonEnabled = false;

        setEnabled(buttonEnabled);
        if (!buttonEnabled && inProgress) {
            textView.setText(textInProgress);
            imageView.setVisibility(VISIBLE);
            valueAnimator.start();
        } else {
            textView.setText(textNormal);
            imageView.setVisibility(GONE);
            valueAnimator.cancel();
        }
    }
}
