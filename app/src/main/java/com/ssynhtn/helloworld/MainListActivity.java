package com.ssynhtn.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainListActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        ButterKnife.bind(this);

        List<MainAdapter.Item> items = new ArrayList<>();
        items.add(new MainAdapter.Item(MainActivity.class.getSimpleName(), MainActivity.class));
        items.add(new MainAdapter.Item(PopupWindowActivity.class.getSimpleName(), PopupWindowActivity.class));
        items.add(new MainAdapter.Item(AlarmActivity.class.getSimpleName(), AlarmActivity.class));
        items.add(new MainAdapter.Item(TextActivity.class.getSimpleName(), TextActivity.class));
        items.add(new MainAdapter.Item(DialogActivity.class.getSimpleName(), DialogActivity.class));
        items.add(new MainAdapter.Item(ScrollingActivity.class.getSimpleName(), ScrollingActivity.class));
        items.add(new MainAdapter.Item(CoordActivity.class.getSimpleName(), CoordActivity.class));
        items.add(new MainAdapter.Item(AnimatorActivity.class.getSimpleName(), AnimatorActivity.class));
        items.add(new MainAdapter.Item(HeaderAdapterActivity.class.getSimpleName(), HeaderAdapterActivity.class));
        items.add(new MainAdapter.Item(StepCircleActivity.class.getSimpleName(), StepCircleActivity.class));
        items.add(new MainAdapter.Item(LifecycleActivity.class.getSimpleName(), LifecycleActivity.class));
        addItem(items, PBActivity.class);
        addItem(items, StartedAndBoundServiceActivity.class);
        addItem(items, BatteryActivity.class);
        addItem(items, JobActivity.class);
        addItem(items, UncaughtActivity.class);
        addItem(items, DiffHeightViewPagerActivity.class);
        addItem(items, TextViewButtonActivity.class);
        addItem(items, NestedScrolling.class);
        addItem(items, MyScrollViewActivity.class);
        addItem(items, EditTextActivity.class);
        addItem(items, LongLinearActivity.class);
        addItem(items, RotateActivity.class);
        addItem(items, ToastActivity.class);
        addItem(items, CounterViewActivity.class);
        addItem(items, SimpleLayoutActivity.class);
        addItem(items, SwitchButtonActivity.class);
        addItem(items, ProgressDialogActivity.class);
        addItem(items, ViewPagerCreateViewTwiceActivity.class);
        addItem(items, TouchButtonActivity.class);
        addItem(items, ScrollBackgroundActivity.class);
        addItem(items, FinishingVsDestroyedActivity.class);
        addItem(items, RecyclerViewTestActivity.class);
        addItem(items, ProgressActivity.class);
        addItem(items, FrameAnimationActivity.class);

        MainAdapter mainAdapter = new MainAdapter(items);

        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mainAdapter);

        // 每次都打开最后一个item
        // 另一个comment
        MainAdapter.Item item = items.get(items.size() - 1);
        Intent intent = new Intent(this, item.clazz);
        startActivity(intent);


    }

    private void addItem(List<MainAdapter.Item> items, Class<?> clazz) {
        items.add(new MainAdapter.Item(clazz.getSimpleName(), clazz));
    }
}
