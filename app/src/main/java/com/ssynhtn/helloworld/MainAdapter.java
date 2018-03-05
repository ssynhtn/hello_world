package com.ssynhtn.helloworld;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huangtongnao on 2017/11/3.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {



    static class Item {
        String name;
        Class<?> clazz;

        public Item(String name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }


    private List<Item> items;

    static interface OnItemClickListener {
        void onClickView(View view, ViewHolder viewHolder);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MainAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textView.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view)
        public TextView textView;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            if (onItemClickListener == null) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Item item = items.get(getAdapterPosition());
                        Intent intent = new Intent(itemView.getContext(), item.clazz);
                        itemView.getContext().startActivity(intent);
                    }
                });

            }
        }

        @OnClick(R.id.text_view)
        public void onViewClicked(View view){
            if (onItemClickListener != null) {
                onItemClickListener.onClickView(view, this);
            }
        }
    }

}
