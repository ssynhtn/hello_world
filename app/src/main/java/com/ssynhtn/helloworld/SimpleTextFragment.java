package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by huangtongnao on 2018/3/28.
 */

public class SimpleTextFragment extends Fragment {

    private static final String TAG = SimpleTextFragment.class.getSimpleName();
    private static final String EXTRA_COUNT = "EXTRA_COUNT";

    public static SimpleTextFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);

        SimpleTextFragment fragment = new SimpleTextFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private int count;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView " + getArguments().getString("text"));
        return inflater.inflate(R.layout.frag_simple_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(EXTRA_COUNT);
        }

        TextView textView = view.findViewById(R.id.text);
        textView.setText(getArguments().getString("text") + " " + this.toString() + ", count " + count);

        Button button = view.findViewById(R.id.btn_click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(EXTRA_COUNT, count);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView " + getArguments().getString("text"));
    }
}
