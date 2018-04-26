package com.ssynhtn.helloworld;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ssynhtn.helloworld.databinding.ActivityDataBindingBinding;
import com.ssynhtn.helloworld.model.User;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setUser(new User(100, 100, "Hello"));
    }
}
