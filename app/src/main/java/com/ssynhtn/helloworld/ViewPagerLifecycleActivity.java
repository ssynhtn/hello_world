package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPagerLifecycleActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private FragmentPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_lifecycle);

        ButterKnife.bind(this);

        adapter = new SimpleAdapter(getSupportFragmentManager(), 10);
        view_pager.setAdapter(adapter);

    }

    @OnClick(R.id.button)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.button: {
                int count = adapter.getCount();
                int index = (int) (Math.random() * count);
                view_pager.setCurrentItem(index, Math.random() > 0.5);
                break;
            }
        }
    }

    static class SimpleAdapter extends FragmentPagerAdapter {

        private final int count;

        public SimpleAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            return LifecycleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
