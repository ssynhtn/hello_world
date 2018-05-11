package com.ssynhtn.helloworld;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TouchViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    TouchViewPager touchViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_view_pager);

        ButterKnife.bind(this);

        touchViewPager.setAdapter(new TouchAdapter(getSupportFragmentManager()));
    }

    static class TouchAdapter extends TouchFragmentPagerAdapter {
        public TouchAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SimpleTextFragment.newInstance("" + position);
        }
    }


}
