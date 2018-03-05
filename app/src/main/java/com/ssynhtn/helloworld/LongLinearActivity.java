package com.ssynhtn.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LongLinearActivity extends AppCompatActivity {

    @BindView(R.id.linear)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_linear);

        ButterKnife.bind(this);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            TextView textView = (TextView) linearLayout.getChildAt(i);
            textView.setText(i + "");
        }

    }
}
