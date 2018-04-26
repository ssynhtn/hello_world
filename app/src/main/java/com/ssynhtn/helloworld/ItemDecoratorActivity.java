package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDecoratorActivity extends AppCompatActivity {

    private static final String TAG = ItemDecoratorActivity.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_decorator);

        ButterKnife.bind(this);

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        List<ComponentName> activities = Utils.readActivities(this);
        Collections.reverse(activities);
        ItemDecoratorAdapter adapter = new ItemDecoratorAdapter(activities);
        adapter.setListener(new ItemDecoratorAdapter.OnClickActivityListener() {
            @Override
            public void onClickActivity(ComponentName activity) {
                jump(activity);
            }
        });
        recycler_view.setAdapter(adapter);
        recycler_view.addItemDecoration(new SepItemDecoration());
    }

    private void jump(ComponentName activity) {
        Intent intent = new Intent();
        intent.setComponent(activity);
        startActivity(intent);
    }

}
