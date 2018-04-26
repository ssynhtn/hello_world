package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class FragmentStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_state);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new SimpleAdapter(getSupportFragmentManager()));

//        RadioGroup radioGroup = findViewById(R.id.radio);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                View child = group.findViewById(checkedId);
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    if (group.getChildAt(i) == child) {
//                        viewPager.setCurrentItem(i, false);
//                        break;
//                    }
//                }
//            }
//        });
    }

    static class SimpleAdapter extends FragmentPagerAdapter {

        private int count;

        public SimpleAdapter(FragmentManager fm) {
            this(fm, 4);
        }

        public SimpleAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }


        @Override
        public Fragment getItem(int position) {
            return SimpleTextFragment.newInstance("" + position);
//            if (position < 2) {
//                return new RecyclerViewExpandFragment();
//            } else {
//                return LazyLoadFragment.newInstance(StateFragment.class, position);

//            }
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
