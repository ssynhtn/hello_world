package com.ssynhtn.helloworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by huangtongnao on 2018/3/6.
 */

public class StateFragment extends Fragment {
    private static final String TAG = StateFragment.class.getSimpleName();
    private void log(String method) {
        Log.d(TAG, "method " + method + ", state " + getStateString());
    }


    public StateFragment() {
        super();

        log("Ctor");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        log("setUserVisibleHint to " + isVisibleToUser + ", isVisible " + isVisible());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        log("onAttach");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        log("onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        log("onActivityCreated");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView");
        return inflater.inflate(R.layout.fragment_state, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        final ProgressBar progressBar = view.findViewById(R.id.progress);
//        progressBar.animate().alpha(0).setDuration(2000).setListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        }).start();
        log("onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();

        log("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        log("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        log("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        log("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        log("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        log("onDestroy");
    }

    private int getState() {

        try {
            Field field = Fragment.class.getDeclaredField("mState");
            field.setAccessible(true);
            return field.getInt(this);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("bad", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("bad", e);
        }

    }


    static enum State {
        INITIALIZING,
        CREATED,
        ACTIVITY_CREATED,
        STOPPED,
        STARTED,
        RESUMED;

    }
    private String getStateString() {
        return State.values()[getState()].toString();
    }
}
