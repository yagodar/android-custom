package com.yagodar.android.custom.fragment.progress;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yagodar.android.custom.R;

/**
 * Created by yagodar on 18.06.2015.
 */
public class ProgressListFragment extends ProgressFragment {

    public ProgressListFragment() {
        super();
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        mList = null;
        mEmptyView = null;
        super.onDestroyView();
    }

    @Override
    public void setContentView(View view) {
        if(view != null && !(view instanceof ListView)) {
            throw new IllegalArgumentException("Content view must be ListView!");
        }

        super.setContentView(view);
        ensureContent();
    }

    @Override
    protected void ensure() {
        super.ensure();
        ensureEmpty();
    }

    @Override
    protected void ensureContent() {
        super.ensureContent();

        if (mList != null && mList.equals(mContentView)) {
            return;
        }

        if(mContentView != null) {
            mList = (ListView) mContentView;
            mList.setOnItemClickListener(mOnClickListener);
            if (mAdapter != null) {
                ListAdapter adapter = mAdapter;
                mAdapter = null;
                setListAdapter(adapter);
            } else {
                // We are starting without an adapter, so assume we won't
                // have our data right away and start with the progress indicator.
                setContentShown(false, false);
            }
            mHandler.post(mRequestFocus);
        } else {
            setContentView(R.layout.progress_fragment_list_view);
        }
    }

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l The ListView where the click happened
     * @param v The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id The row id of the item that was clicked
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    /**
     * Get the ListAdapter associated with this activity's ListView.
     */
    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    /**
     * Provide the cursor for the list view.
     */
    public void setListAdapter(ListAdapter listAdapter) {
        boolean hadAdapter = mAdapter != null;
        mAdapter = listAdapter;
        if (mList != null) {
            mList.setAdapter(listAdapter);
            if (!mContentShown && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setContentShown(true, getView().getWindowToken() != null);
            }
        }
    }

    /**
     * Set the currently selected list item to the specified
     * position with the adapter's data
     *
     * @param position
     */
    public void setSelection(int position) {
        ensure();
        mList.setSelection(position);
    }

    /**
     * Get the position of the currently selected list item.
     */
    public int getSelectedItemPosition() {
        ensure();
        return mList.getSelectedItemPosition();
    }

    /**
     * Get the cursor row ID of the currently selected list item.
     */
    public long getSelectedItemId() {
        ensure();
        return mList.getSelectedItemId();
    }

    /**
     * Get the activity's list view widget.
     */
    public ListView getListView() {
        return mList;
    }

    public void setEmptyText(int resId) {
        setEmptyText(getString(resId));
    }

    /**
     * The default content for a ListFragment has a TextView that can
     * be shown when the list is empty.  If you would like to have it
     * shown, call this method to supply the text it should use.
     */
    public void setEmptyText(CharSequence text) {
        ensure();
        mEmptyView.setText(text);
    }

    private void ensureEmpty() {
        if(mEmptyView != null) {
            return;
        }

        View root = getView();
        mEmptyView = (TextView)root.findViewById(android.R.id.empty);

        if(mEmptyView == null) {
            throw new IllegalStateException("Can't be used with a custom content view!");
        }

        mList.setEmptyView(mEmptyView);
    }

    private ListAdapter mAdapter;
    private ListView mList;
    private TextView mEmptyView;

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mList.focusableViewAvailable(mList);
        }
    };

    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView)parent, v, position, id);
        }
    };
}
