package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InteractiveChartActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    InteractiveLineGraphView interactiveLineGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_chart);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_1: {
                interactiveLineGraphView.zoomIn();
                break;
            }

            case R.id.btn_2:
                interactiveLineGraphView.zoomOut();
                break;

            case R.id.btn_3:
                interactiveLineGraphView.panLeft();
                break;

            case R.id.btn_4:
                interactiveLineGraphView.panRight();
                break;

            case R.id.btn_5:
                interactiveLineGraphView.panUp();
                break;

            case R.id.btn_6:
                interactiveLineGraphView.panDown();
                break;
        }
    }


}
