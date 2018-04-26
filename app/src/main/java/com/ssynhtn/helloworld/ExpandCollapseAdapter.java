package com.ssynhtn.helloworld;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by huangtongnao on 2018/3/6.
 */

public class ExpandCollapseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SHORT_SIZE = 3;

    private List<String> data;

    private boolean expanded = false;

    public ExpandCollapseAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (!isShortList() && position == getItemCount() - 1) {
            return R.layout.item_expand_button;
        } else {
            return R.layout.item_text;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        switch (viewType) {
            case R.layout.item_expand_button: {
                final ExpandViewHolder expandViewHolder = new ExpandViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (expanded) {
                            expanded = false;
                            int removeCount = data.size() - SHORT_SIZE;
                            notifyItemRangeRemoved(SHORT_SIZE, removeCount);
                        } else {
                            expanded = true;
                            int insertCount = data.size() - SHORT_SIZE;
                            notifyItemRangeInserted(SHORT_SIZE, insertCount);
                        }
                    }
                });
                return expandViewHolder;
            }

            case R.layout.item_text: {
                return new TextViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == R.layout.item_text) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.bindData(data.get(position));
        }
    }

    private boolean isShortList() {
        return data.size() <= SHORT_SIZE;
    }

    @Override
    public int getItemCount() {
        if (isShortList()) {
            return data.size();
        }

        if (expanded) {
            return data.size() + 1;
        } else {
            return SHORT_SIZE + 1;
        }
    }

    static class ExpandViewHolder extends RecyclerView.ViewHolder {

        public ExpandViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView;
        }

        public void bindData(String text) {
            textView.setText(text);
        }
    }
}
