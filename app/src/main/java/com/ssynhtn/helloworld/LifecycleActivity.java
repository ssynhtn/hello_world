package com.ssynhtn.helloworld;

//import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

//        Log.d(TAG, "state " + getLifecycle().getCurrentState() + " at least " + getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED));
    }

}
