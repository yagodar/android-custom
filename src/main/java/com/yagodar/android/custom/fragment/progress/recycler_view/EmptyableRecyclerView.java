package com.yagodar.android.custom.fragment.progress.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.yagodar.android.custom.fragment.progress.emptyable_view.IEmptyableView;

/**
 * Created by yagodar on 29.04.2016.
 */
public class EmptyableRecyclerView extends RecyclerView implements IEmptyableView {
    private View mEmptyView;
    private final AdapterDataObserver mEmptyDataObserver = new EmptyDataObserver();

    public EmptyableRecyclerView(Context context) {
        super(context);
    }

    public EmptyableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mEmptyDataObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mEmptyDataObserver);
        }
        super.setAdapter(adapter);
        updateEmptyStatus();
    }

    @Override
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        updateEmptyStatus();
    }

    private void updateEmptyStatus() {
        Adapter adapter = getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);
            }
        } else {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }
            setVisibility(View.VISIBLE);
        }
    }

    private class EmptyDataObserver extends AdapterDataObserver {
        @Override
        public void onChanged() {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyStatus();
        }
    }
}
