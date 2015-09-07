package com.yagodar.android.custom.fragment.progress.recycler_view;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yagodar.android.custom.R;
import com.yagodar.android.custom.fragment.progress.common_view.ProgressFragment;

/**
 * Created by yagodar on 03.09.2015.
 */
public class ProgressRecyclerViewFragment extends ProgressFragment {

    public ProgressRecyclerViewFragment() {
        super();
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        mRecyclerView = null;
        super.onDestroyView();
    }

    @Override
    public void setContentView(View view) {
        if(view != null && !(view instanceof RecyclerView)) {
            throw new IllegalArgumentException("Content view must be RecyclerView!");
        }

        super.setContentView(view);
        ensureContent();
    }

    @Override
    protected void ensureContent() {
        super.ensureContent();

        if (mRecyclerView != null && mRecyclerView.equals(mContentView)) {
            return;
        }

        if(mContentView != null) {
            mRecyclerView = (RecyclerView) mContentView;
            if (mRecyclerAdapter != null) {
                RecyclerView.Adapter adapter = mRecyclerAdapter;
                mRecyclerAdapter = null;
                setRecyclerAdapter(adapter);
            } else {
                // We are starting without an adapter, so assume we won't
                // have our data right away and start with the progress indicator.
                setContentShown(false, false);
            }
            mHandler.post(mRequestFocus);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            RecyclerView recyclerView = (RecyclerView) layoutInflater.inflate(R.layout.progress_fragment_recycle_view, null);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            setContentView(recyclerView);
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
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(recyclerAdapter);
            if (!mContentShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setContentShown(true, getView().getWindowToken() != null);
            }
        }
    }

    /**
     * Get the activity's RecyclerView widget.
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mRecyclerView.focusableViewAvailable(mRecyclerView);
        }
    };
}
