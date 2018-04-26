package com.ssynhtn.helloworld;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangtongnao on 2018/4/16.
 */

public class LifecycleFragment extends ViewPagerLifecycleFragment implements LifecycleObserver {
    private static final String EXTRA_INDEX = "EXTRA_INDEX";

    @BindView(R.id.text_view)
    TextView textView;

    public static LifecycleFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_INDEX, index);

        LifecycleFragment fragment = new LifecycleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LifecycleFragment() {
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getName(), "onCreate");

        ViewModelProviders.of(this).get(SimpleViewModel.class).getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(getName(), "got string " + s);
                textView.setText(s);

            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onLifeEvent(LifecycleOwner owner, Lifecycle.Event event) {
        Log.d(getName(), " event " + event);
    }

    protected String getName() {
        String index;
        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt(EXTRA_INDEX) + "";
        } else {
            index = this.toString();
        }

        return getClass().getSimpleName() + " " + index;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_lifecycle, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getName(), "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getName(), "onDestroy");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        TextView textView = (TextView) view;
        textView.setText(getName());
    }
}
