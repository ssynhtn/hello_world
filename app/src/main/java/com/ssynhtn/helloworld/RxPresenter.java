package com.ssynhtn.helloworld;

import android.arch.lifecycle.LifecycleObserver;

/**
 * Created by huangtongnao on 2018/3/16.
 */

public interface RxPresenter extends LifecycleObserver {
    void testLeak();
}
