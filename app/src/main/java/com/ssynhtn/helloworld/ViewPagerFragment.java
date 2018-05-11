package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangtongnao on 2018/5/2.
 */

public class ViewPagerFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        view_pager.setAdapter(new MyAdapter(getChildFragmentManager()));
        view_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN && v instanceof ViewGroup) {
//                    ViewPager parent = findParentViewPager(v);
//                    if (parent != null) {
//                        parent.requestDisallowInterceptTouchEvent(true);
//                    } else {
//                        Toast.makeText(getContext(), "found null", Toast.LENGTH_SHORT).show();
//                    }

                    ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                }

                return false;
            }
        });


    }

    private ViewPager findParentViewPager(View v) {
        v = (View) v.getParent();
        while (v != null && !(v instanceof ViewPager)) {
            v = (View) v.getParent();
        }

        if (v != null) {
            return (ViewPager) v;
        } else {
            return null;
        }
    }

    private static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SimpleTextFragment.newInstance("" + position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
