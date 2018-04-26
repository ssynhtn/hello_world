package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NestedScrolling extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling);

        ButterKnife.bind(this);

        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.getLayoutManager().setAutoMeasureEnabled(true);

        final List<MainAdapter.Item> items = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            items.add(new MainAdapter.Item(PieActivity.class.getSimpleName() + i, PieActivity.class));
        }

        final MainAdapter mainAdapter = new MainAdapter(items);
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onClickView(View view, MainAdapter.ViewHolder viewHolder) {
                int pos = viewHolder.getAdapterPosition();

                items.remove(pos);
                mainAdapter.notifyItemRemoved(pos);
            }
        });
        recycler_view.setAdapter(mainAdapter);
    }
}
