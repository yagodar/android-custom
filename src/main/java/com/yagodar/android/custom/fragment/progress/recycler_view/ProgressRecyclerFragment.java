package com.yagodar.android.custom.fragment.progress.recycler_view;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yagodar.android.custom.R;
import com.yagodar.android.custom.fragment.progress.emptyable_view.ProgressEmptyableFragment;

/**
 * Created by yagodar on 03.09.2015.
 */
public class ProgressRecyclerFragment extends ProgressEmptyableFragment<EmptyableRecyclerView> {

    public ProgressRecyclerFragment() {
        super();
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        super.onDestroyView();
    }

    @Override
    protected void ensureContent() {
        if(isContentEnsured()) {
            return;
        }
        super.ensureContent();
        EmptyableRecyclerView contentView = getContentView();
        if(contentView != null) {
            contentView.setHasFixedSize(true);
            contentView.setLayoutManager(new LinearLayoutManager(getContext()));
            contentView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            if (mRecyclerAdapter != null) {
                RecyclerView.Adapter adapter = mRecyclerAdapter;
                mRecyclerAdapter = null;
                setRecyclerAdapter(adapter);
            } else {
                // We are starting without an adapter, so assume we won't
                // have our data right away and start with the progress indicator.
                setContentShown(false);
            }
            mHandler.post(mRequestFocus);
        } else {
            setContentView(R.layout.progress_fragment_recycle_view);
        }
    }

    /**
     * Get the RecyclerView.Adapter associated with this activity's RecyclerView.
     */
    public RecyclerView.Adapter getRecycleAdapter() {
        return mRecyclerAdapter;
    }

    /**
     * Provide the cursor for the RecyclerView.
     */
    public void setRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {
        boolean hadAdapter = mRecyclerAdapter != null;
        mRecyclerAdapter = recyclerAdapter;
        EmptyableRecyclerView contentView = getContentView();
        if (contentView != null) {
            contentView.setAdapter(recyclerAdapter);
            if (!isContentShown() && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setContentShown(true);
            }
        }
    }

    private RecyclerView.Adapter mRecyclerAdapter;

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            EmptyableRecyclerView contentView = getContentView();
            contentView.focusableViewAvailable(contentView);
        }
    };
}
