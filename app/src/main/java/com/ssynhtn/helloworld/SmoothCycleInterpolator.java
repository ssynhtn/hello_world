package com.ssynhtn.helloworld;

import android.view.animation.Interpolator;

/**
 * Created by huangtongnao on 2018/1/6.
 * 不知道为啥测试效果不是很好
 */

public class SmoothCycleInterpolator implements Interpolator {
    private int cycle;

    public SmoothCycleInterpolator(int cycle) {
        this.cycle = cycle;
    }

    @Override
    public float getInterpolation(float input) {
        return (float)(Math.sin(2 * cycle * Math.PI * input));
//        float t = input * cycle;
//        int i = (int) t;
//        t = t - i;  // t in [0, 1]
//
//        return smoothFunctionTwo(t);
    }

    private float smoothFunctionTwo(float t) {
        int sign = t < 0.5f ? 1 : -1;
        double val = (1 - Math.cos(t * 4 * Math.PI)) / 2;
        return (float) (val * sign);
    }

    private float smoothFunction(float t) {
        float max = (float) (4 * Math.PI * Math.PI);
        float u = (float) (16 * Math.PI * t);

        return foo(u) / max;
    }

    private float foo(float u) {

        if (u < 0 || u > Math.PI * 16) return 0;

        if (u <= 2 * Math.PI) {
            return (float) (u * u / 2 + Math.cos(u) - 1);
        } else if (u <= 4 * Math.PI) {
            return (float) (4 * Math.PI * u - u * u / 2 - Math.cos(u) + 1 - 4 * Math.PI * Math.PI);
        } else if (u <= 8 * Math.PI) {
            return foo((float) (Math.min(8 * Math.PI - u, 4 * Math.PI)));
        } else {
            return -foo((float) (Math.min(16 * Math.PI - u, 8 * Math.PI)));
        }
    }

}
