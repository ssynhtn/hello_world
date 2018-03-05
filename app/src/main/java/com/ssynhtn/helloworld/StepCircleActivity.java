package com.ssynhtn.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepCircleActivity extends AppCompatActivity {

    @BindView(R.id.step)
    StepCircleView stepCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_circle);

        ButterKnife.bind(this);

        stepCircleView.setTargetSteps(8000);
        stepCircleView.setSportsData(1083, 809, 31);

        stepCircleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: return true;
                    case MotionEvent.ACTION_UP: {
                        stepCircleView.setSportsData((int) (Math.random() * 15000), (int) (Math.random() * 6000), (int) (Math.random() * 100));
                        return true;
                    }
                    default: break;
                }
                return false;
            }
        });
    }
}
