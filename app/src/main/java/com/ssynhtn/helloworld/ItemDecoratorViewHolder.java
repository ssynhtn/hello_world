package com.ssynhtn.helloworld;

import android.content.ComponentName;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huangtongnao on 2018/4/13.
 */

public class ItemDecoratorViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view)
    TextView text_view;

    public ItemDecoratorViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setActivity(ComponentName activity) {
        try {
            Class<?> clazz = Class.forName(activity.getClassName());
            text_view.setText(clazz.getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            text_view.setText(activity.getClassName());
        }

    }
}
