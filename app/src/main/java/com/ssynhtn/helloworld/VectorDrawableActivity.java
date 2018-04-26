package com.ssynhtn.helloworld;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VectorDrawableActivity extends AppCompatActivity {

    @BindView(R.id.image_view)
    ImageView image_view;

    @BindView(R.id.blocking_view)
    View blocking_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);

        ButterKnife.bind(this);

        image_view.setColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN);

        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VectorDrawableActivity.this, "click", Toast.LENGTH_SHORT).show();
                blocking_view.setVisibility(View.VISIBLE);
            }
        });

        blocking_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blocking_view.setVisibility(View.GONE);
            }
        });
    }
}
