package com.ssynhtn.helloworld;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by huangtongnao on 2018/3/7.
 */

public class LazyLoadFragment extends Fragment {

    private static final String EXTRA_FRAG_CLASS = "EXTRA_FRAG_CLASS";
    private String TAG = LazyLoadFragment.class.getSimpleName();
    private static final String EXTRA_INDEX = "EXTRA_INDEX";

    public static LazyLoadFragment newInstance(Class<? extends Fragment> clazz, int index) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_FRAG_CLASS, clazz);
        args.putInt(EXTRA_INDEX, index);

        LazyLoadFragment fragment = new LazyLoadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView, visibilityHint " + getUserVisibleHint());
        return inflater.inflate(R.layout.fragment_lazy_load, container, false);
    }


    private View progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);

        if (getUserVisibleHint()) {
            addFragment("onViewCreated", false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.d(TAG, "setVisibilityHint " + isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            addFragment("setUserVisibleHint", true);
        }
    }


    private void addFragment(String cause, boolean animate) {
        Log.d(TAG, "ready to add fragment by cause " + cause);
        if (getChildFragmentManager().findFragmentById(R.id.container) == null) {
            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .add(R.id.container, createFragment()).commit();
            Log.d(TAG, "added fragment by cause " + cause);
            boolean test = true;
            if (animate) {
                progressBar.animate().alpha(0).setDuration(getResources().getInteger(android.R.integer.config_longAnimTime)).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        progressBar.setVisibility(View.GONE);
                    }
                }).start();
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }


    private Fragment createFragment() {
        Class<? extends Fragment> clazz = getExtraFragClass();
        try {
            return clazz.getConstructor().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Class<? extends Fragment> getExtraFragClass() {
        Bundle args = getArguments();
        if (args != null) {
            return (Class<? extends Fragment>) args.getSerializable(EXTRA_FRAG_CLASS);
        }

        return null;
    }
}
