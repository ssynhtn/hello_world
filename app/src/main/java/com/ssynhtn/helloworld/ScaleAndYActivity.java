package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScaleAndYActivity extends AppCompatActivity {

    private static final long ANIMATION_DURATION = 1000;
    @BindView(R.id.view)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_and_y);

        ButterKnife.bind(this);

        view.setY(40);
        view.setScaleX(0.9f);
        view.setScaleY(0.9f);

    }

    @OnClick({R.id.btn_show, R.id.btn_hide})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_show: {
                showView(this.view);
                break;
            }

            case R.id.btn_hide: {
                hideView(this.view);
                break;
            }
        }
    }

    private void showView(final View view) {
        view.animate().alpha(1).setDuration(ANIMATION_DURATION).start();;
    }

    private void hideView(final View view) {
        view.animate().alpha(0).setDuration(ANIMATION_DURATION).start();
    }

}
