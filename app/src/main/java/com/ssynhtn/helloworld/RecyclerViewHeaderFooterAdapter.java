package com.ssynhtn.helloworld;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangtongnao on 2017/11/7.
 */

public abstract class RecyclerViewHeaderFooterAdapter extends RecyclerView.Adapter<RecyclerViewHeaderFooterAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewHeaderFooterAdapter.class.getSimpleName();

    protected List<View> headerViews = new ArrayList<>();
    protected Map<View, Integer> headerViewsVisibility = new HashMap<>();
    protected List<View> footerViews = new ArrayList<>();
    protected Map<View, Integer> footerViewsVisibility = new HashMap<>();

    public void addHeaderView(View view) {
        headerViews.add(view);
        headerViewsVisibility.put(view, View.VISIBLE);
        notifyItemInserted(headerViews.size() - 1);
    }
    public void addFooterView(View view) {
        footerViews.add(view);
        footerViewsVisibility.put(view, View.VISIBLE);
        notifyItemInserted(getItemCount() - 1);
    }

    protected static final int VIEW_TYPE_FOOTER_EDN = -1000; // footer view 占用(-Infinity, -1000]这个范围
    protected static final int VIEW_TYPE_HEADER_END = 0;    // header view 占用[-999, 0]这个范围
    protected static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public RecyclerViewHeaderFooterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType) || isFooterViewType(viewType)) {
            View view = getHeaderFooterViewFromType(viewType);
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            FrameLayout.LayoutParams params = null;
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                params = (FrameLayout.LayoutParams) layoutParams;
            } else if (layoutParams != null) {
                params = new FrameLayout.LayoutParams(layoutParams);
            } else {
                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            if (view.getParent() instanceof FrameLayout) {
                FrameLayout lastFrameLayout = (FrameLayout) view.getParent();
                lastFrameLayout.removeView(view);
            }
            frameLayout.addView(view, params);
            return new HeaderFooterViewHolder(frameLayout);
        }

        return onCreateNormalViewHolder(parent, viewType);
    }

    private View getHeaderFooterViewFromType(int viewType) {
        if (isHeaderViewType(viewType)) {
            return headerViews.get(getHeaderViewPosition(viewType));
        } else if (isFooterViewType(viewType)) {
            return footerViews.get(getFooterViewPosition(viewType));
        }
        return null;
    }

    protected abstract ViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPosition(position)) {
            View header = headerViews.get(position);
            header.setVisibility(headerViewsVisibility.get(header));
        } else if (isFooterViewPosition(position)) {
            int footerListIndex = position - headerViews.size() - getNonHeaderItemCount();
            View footer = footerViews.get(footerListIndex);
            footer.setVisibility(footerViewsVisibility.get(footer));
        } else {
            onBindNormalViewHolder(holder, getNormalViewPosition(position));
        }
    }

    private int getNormalViewPosition(int position) {
        return position - getHeaderCount();
    }

    protected abstract void onBindNormalViewHolder(ViewHolder holder, int normalViewPosition);

    @Override
    public int getItemCount() {
        return getHeaderCount() + getNonHeaderItemCount() + footerViews.size();
    }

    protected int getHeaderCount() {
        return headerViews.size();
    }
    protected abstract int getNonHeaderItemCount();

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPosition(position)) {
            return VIEW_TYPE_HEADER_END - position;
        } else if (isFooterViewPosition(position)) {
            return VIEW_TYPE_FOOTER_EDN - (position - getHeaderCount() - getNonHeaderItemCount());
        } else {
            return getNormalViewType(position);
        }
    }

    private int getNormalViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    protected boolean isHeaderViewType(int viewType) {
        return viewType <= VIEW_TYPE_HEADER_END && viewType > VIEW_TYPE_FOOTER_EDN;
    }
    protected boolean isFooterViewType(int viewType) {
        return viewType <= VIEW_TYPE_FOOTER_EDN;
    }

    protected boolean isHeaderViewPosition(int pos) {
        return pos < getHeaderCount();
    }

    protected boolean isFooterViewPosition(int pos) {
        return pos >= getHeaderCount() + getNonHeaderItemCount();
    }

    protected int getHeaderViewPosition(int viewType) {
        return VIEW_TYPE_HEADER_END - viewType;
    }
    protected int getFooterViewPosition(int viewType) {
        return VIEW_TYPE_FOOTER_EDN - viewType;
    }

    public int getHeaderViewPosition(View view) {
        return headerViews.indexOf(view);
    }
    public int getFooterViewPosition(View view) {
        return headerViews.size() + getNonHeaderItemCount() + footerViews.indexOf(view);
    }

    public void hideFooterView(View footer) {
        if (!footerViewsVisibility.containsKey(footer)) {
            return;
        }

        if (footerViewsVisibility.get(footer) == View.GONE) return;
        footerViewsVisibility.put(footer, View.GONE);
        int pos = headerViews.size() + getNonHeaderItemCount() + footerViews.indexOf(footer);
        notifyItemChanged(pos);

    }
    public void showFooterView(View footer) {
        if (!footerViewsVisibility.containsKey(footer)) {
            return;
        }

        if (footerViewsVisibility.get(footer) == View.VISIBLE) return;
        footerViewsVisibility.put(footer, View.VISIBLE);
        int pos = headerViews.size() + getNonHeaderItemCount() + footerViews.indexOf(footer);
        notifyItemChanged(pos);
    }
    public void hideHeaderView(View header) {
        if (!headerViewsVisibility.containsKey(header)) {
            return;
        }

        if (headerViewsVisibility.get(header) == View.GONE) return;
        headerViewsVisibility.put(header, View.GONE);
        int pos = headerViews.indexOf(header);
        notifyItemChanged(pos);
    }
    public void showHeaderView(View header) {
        if (!headerViewsVisibility.containsKey(header)) {
            return;
        }

        if (headerViewsVisibility.get(header) == View.VISIBLE) return;
        headerViewsVisibility.put(header, View.VISIBLE);
        int pos = headerViews.indexOf(header);
        notifyItemChanged(pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderFooterViewHolder extends ViewHolder {
        View contentView;
        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);

            if (itemView instanceof ViewGroup) {
                contentView = ((ViewGroup) itemView).getChildAt(0);
            }
        }
    }
}
