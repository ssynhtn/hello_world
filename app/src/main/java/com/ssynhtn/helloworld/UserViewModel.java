package com.ssynhtn.helloworld;

import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.ssynhtn.helloworld.model.User;

/**
 * Created by huangtongnao on 2018/3/29.
 */

public class UserViewModel extends ViewModel {
    private UserLiveData userLiveData;
    public int randInt;

    public UserViewModel() {
        randInt = (int) (Math.random() * 100);

        userLiveData = new UserLiveData();

//        fetchUser();
    }

    public UserLiveData getUserLiveData() {
        return userLiveData;
    }

    public void fetchUser() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.id = (int) (Math.random() * 100);
                user.age = (int) (Math.random() * 40);
                user.name = String.format("%.2f", Math.random());
                userLiveData.setValue(user);
            }
        }, (long) (1500 + 1000 * Math.random()));
    }


    @Override
    public String toString() {
        return super.toString() + " " + randInt;
    }


    public UserLiveData searchUsers() {
        // todo
        if (userLiveData == null) {
//            userLiveData = ..................
        }

        return userLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
