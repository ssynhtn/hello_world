package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coord);

        ButterKnife.bind(this);

        List<MainAdapter.Item> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(new MainAdapter.Item(MainActivity.class.getSimpleName(), MainActivity.class));
        }

        MainAdapter mainAdapter = new MainAdapter(items);

        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mainAdapter);
    }
}
