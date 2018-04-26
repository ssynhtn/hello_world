package com.ssynhtn.helloworld;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerTestActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);

        ButterKnife.bind(this);

        view_pager.setAdapter(new Adapter(getSupportFragmentManager()));


    }

    private class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new NoClickFragment();
            } else {
                return new DrawerFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
