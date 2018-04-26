package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.constraint.Placeholder;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceholderActivity extends AppCompatActivity {

    @BindView(R.id.place_holder)
    Placeholder place_holder;

    @BindView(R.id.container)
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_1, R.id.button2, R.id.button3})
    public void onClick(Button button) {
        TransitionManager.beginDelayedTransition(container);
        place_holder.setContentId(button.getId());
    }
}
