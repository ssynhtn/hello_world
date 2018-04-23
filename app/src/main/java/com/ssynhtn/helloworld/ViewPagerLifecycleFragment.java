package com.langgan.haoshuimian.MVP.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.langgan.haoshuimian.fragment.BaseFragment;

/**
 * Created by huangtongnao on 2018/4/16.
 * 处理FragmentPagerAdapter中setUserVisibleHint导致的用户观察到的生命周期和实际生命周期不一样的问题
 */

public abstract class ViewPagerLifecycleFragment extends BaseFragment implements LifecycleObserver {
    private LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    public ViewPagerLifecycleFragment() {
        super.getLifecycle().addObserver(this);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mRegistry;
    }

    public Lifecycle getFragmentLifecycle() {
        return super.getLifecycle();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onLifecycleEvent(LifecycleOwner owner, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_DESTROY:
                mRegistry.handleLifecycleEvent(event);
                break;
            case ON_START:
            case ON_RESUME:
            case ON_PAUSE:
            case ON_STOP:
                if (getUserVisibleHint()) {
                    mRegistry.handleLifecycleEvent(event);
                }
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Lifecycle.State fragmentState = super.getLifecycle().getCurrentState();
        if (isVisibleToUser) {
            switch (fragmentState) {
                case STARTED: {
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                    break;
                }
                case RESUMED: {
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                    break;
                }
            }
        } else {
            switch (fragmentState) {
                case STARTED: {
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                    break;
                }
                case RESUMED: {
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
                    mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                    break;
                }
            }
        }
    }


}
