package com.ssynhtn.helloworld;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

/**
 * Created by huangtongnao on 2018/4/16.
 */

public class SimpleViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public SimpleViewModel() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                liveData.setValue("hello " + (int) (Math.random() * 100));
            }
        }, 1000);
    }

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }
}
