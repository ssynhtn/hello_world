package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by huangtongnao on 2018/4/13.
 */

public class ItemDecoratorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ComponentName> activities;

    public interface OnClickActivityListener {
        void onClickActivity(ComponentName activity);
    }

    private OnClickActivityListener listener;

    public void setListener(OnClickActivityListener listener) {
        this.listener = listener;
    }

    public ItemDecoratorAdapter(List<ComponentName> activities) {
        this.activities = activities;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        final ItemDecoratorViewHolder viewHolder = new ItemDecoratorViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.getAdapterPosition() != RecyclerView.NO_POSITION && listener != null) {
                    listener.onClickActivity(activities.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemDecoratorViewHolder viewHolder = (ItemDecoratorViewHolder) holder;
        viewHolder.setActivity(activities.get(position));
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }
}
