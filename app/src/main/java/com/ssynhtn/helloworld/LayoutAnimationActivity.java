package com.ssynhtn.helloworld;

import android.animation.LayoutTransition;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LayoutAnimationActivity extends AppCompatActivity {

    TextView textView;
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);

        textView = findViewById(R.id.tv);
        container = findViewById(R.id.container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        }

        final String longText = getString(R.string.large_text).substring(0, 400);
        final String shortText = longText.substring(0, 100);

        textView.setText(shortText);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals(shortText)) {
                    textView.setText(longText);
                } else {
                    textView.setText(shortText);
                }
            }
        });

    }
}
