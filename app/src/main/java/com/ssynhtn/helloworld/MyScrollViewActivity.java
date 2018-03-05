package com.ssynhtn.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyScrollViewActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.recycler_view_two)
    RecyclerView recycler_view_two;

    @BindView(R.id.tv_doc_desc)
    TextView tv_doc_desc;

    @BindView(R.id.tv_doc_desc_open)
    TextView tv_doc_desc_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scroll_view);

        ButterKnife.bind(this);

        recycler_view.post(new Runnable() {
            @Override
            public void run() {
                setupRecyclerView(recycler_view);
                setupRecyclerView(recycler_view_two);
            }
        });

    }


    private void setupRecyclerView(RecyclerView recycler_view) {
        recycler_view.setLayoutManager(recycler_view == this.recycler_view ? new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) : new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false));
        recycler_view.setHasFixedSize(true);
        recycler_view.setNestedScrollingEnabled(false);

        final List<MainAdapter.Item> items = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            items.add(new MainAdapter.Item("" + i, MainActivity.class));
        }

        final MainAdapter mainAdapter = new MainAdapter(items);
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onClickView(View view, MainAdapter.ViewHolder viewHolder) {
                int pos = viewHolder.getAdapterPosition();

                if (pos >= 0 && pos < items.size()) {
//                    items.remove(pos);
                    items.add(pos + 1, new MainAdapter.Item("hello" + (pos + 1), String.class));
                }
                mainAdapter.notifyItemInserted(pos + 1);
            }
        });
        recycler_view.setAdapter(mainAdapter);
    }

    @OnClick(R.id.btn_open_close)
    public void onClick(View view) {
        if (view.getId() == R.id.btn_open_close) {
            if (tv_doc_desc.getVisibility() == View.VISIBLE) {
                tv_doc_desc.setVisibility(View.GONE);
                tv_doc_desc_open.setVisibility(View.VISIBLE);
//                recycler_view_two.requestLayout();
            } else {
                tv_doc_desc.setVisibility(View.VISIBLE);
                tv_doc_desc_open.setVisibility(View.GONE);
            }
        }
    }
}
