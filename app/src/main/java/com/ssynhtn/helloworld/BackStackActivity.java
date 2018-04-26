package com.ssynhtn.helloworld;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ssynhtn.helloworld.model.User;

public class BackStackActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BackStackActivity.class.getSimpleName();


    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstack);

        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_search);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn_hide).setOnClickListener(this);
        findViewById(R.id.btn_show).setOnClickListener(this);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Log.d(TAG, "userViewModel " + userViewModel);


        userViewModel.getUserLiveData().searchUsers(RxTextView.textChanges(editText));
        userViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Log.d(TAG, "user changed to " + user);
                textView.setText("User " + user);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        textView = null;
    }

    private int count;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn: {
                count++;
                String tag = count + "";
                getSupportFragmentManager().beginTransaction().add(R.id.container, SimpleTextFragment.newInstance(tag), tag).commit();
                break;
            }
            case R.id.btn_hide: {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("1");
                if (fragment != null) {
                    oldFragment = fragment;
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                break;
            }
            case R.id.btn_show: {
                if (oldFragment != null) {
                    getSupportFragmentManager().beginTransaction().add(R.id.container, oldFragment).commit();
                    oldFragment = null;
                }
                break;
            }
        }
    }
    private Fragment oldFragment;
}
