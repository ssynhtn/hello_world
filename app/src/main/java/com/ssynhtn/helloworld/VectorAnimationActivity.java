package com.ssynhtn.helloworld;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VectorAnimationActivity extends AppCompatActivity {


    @BindView(R.id.image_view)
    ImageView image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_animation);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.image_view)
    public void onClick(View view) {
        play();
    }

    private void play() {
        Animatable drawable = (Animatable) image_view.getDrawable();
        drawable.start();
    }

}
