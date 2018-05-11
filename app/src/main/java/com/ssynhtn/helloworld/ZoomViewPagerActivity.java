package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZoomViewPagerActivity extends AppCompatActivity {

    private static final String TAG = ZoomViewPagerActivity.class.getSimpleName();

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_view_pager);

        ButterKnife.bind(this);

        view_pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        view_pager.setPageTransformer(false, new TranslationPageTransformer());

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float translation = position + positionOffset;
                if (translation < 0) {
                    translation = 0;
                } else if (translation > 1) {
                    translation = 1;
                }

                blur(translation);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     *
     * @param degree 模糊程度, 0~1
     */
    private void blur(float degree) {

    }

    private static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SimpleTextFragment.newInstance("" + position);
            }

            return new ViewPagerFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public float getPageWidth(int position) {
            if (position == 0) return 1;
            return 0.8f;
        }
    }
}
