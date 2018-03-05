package com.ssynhtn.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangtongnao on 2017/11/7.
 */

public class HeaderAdapter extends RecyclerViewHeaderFooterAdapter {


    private int count;

    public HeaderAdapter(int count) {
        this.count = count;
    }

    @Override
    protected ViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    protected void onBindNormalViewHolder(ViewHolder holder, int normalViewPosition) {
        SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
        viewHolder.textView.setText(Integer.toString(normalViewPosition));
    }

    @Override
    protected int getNonHeaderItemCount() {
        return count;
    }

    class SimpleViewHolder extends ViewHolder {
        @BindView(R.id.text_view)
        TextView textView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
