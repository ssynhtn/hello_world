package com.ssynhtn.helloworld;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZoomViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_view_pager);

        ButterKnife.bind(this);

        view_pager.setAdapter(new FragmentStateActivity.SimpleAdapter(getSupportFragmentManager()));
        view_pager.setPageTransformer(false, new ZoomOutPageTransformer());
    }
}
