package com.ssynhtn.helloworld;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridSepItemDecorationActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_sep_item_decoration);

        ButterKnife.bind(this);

        recycler_view.setLayoutManager(new GridLayoutManager(this, 3));
        recycler_view.setAdapter(new ItemDecoratorAdapter(Utils.readActivities(this)));
        recycler_view.addItemDecoration(new GridItemSepDecoration(Color.parseColor("#30ff0000"), getResources().getDimensionPixelSize(R.dimen.sep_height)));
    }
}
