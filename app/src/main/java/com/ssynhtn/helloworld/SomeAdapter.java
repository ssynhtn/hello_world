package com.ssynhtn.helloworld;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.hfrecyclerview.HFRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangtongnao on 2017/11/13.
 */

public class SomeAdapter extends HFRecyclerView<String> {

    private final View header;
    private final View footer;

    public SomeAdapter(List<String> data, View header, View footer) {
        super(data, true, true);

        this.header = header;
        this.footer = footer;
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HFViewHolder(header);
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new HFViewHolder(footer);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).textView.setText(Integer.toString(position));
        }
    }

    class HFViewHolder extends RecyclerView.ViewHolder {

        public HFViewHolder(View itemView) {
            super(itemView);
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
