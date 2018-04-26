package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerRotateActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    public ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_rotate);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            view_pager.setAdapter(new Adapter(getSupportFragmentManager()));
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view_pager.setAdapter(new Adapter(getSupportFragmentManager()));
                }
            }, 2000);
        }

    }

    private static class Adapter extends FragmentPagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SimpleTextFragment.newInstance(position + "");
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
