package com.yagodar.android.custom.fragment.progress.list_view;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yagodar.android.custom.R;
import com.yagodar.android.custom.fragment.progress.emptyable_view.ProgressEmptyableFragment;

/**
 * Created by yagodar on 18.06.2015.
 */
public class ProgressListFragment extends ProgressEmptyableFragment<EmptyableListView> {

    public ProgressListFragment() {
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
        ListView contentView = getContentView();
        if(contentView != null) {
            contentView.setOnItemClickListener(mOnClickListener);
            if (mListAdapter != null) {
                ListAdapter adapter = mListAdapter;
                mListAdapter = null;
                setListAdapter(adapter);
            } else {
                // We are starting without an adapter, so assume we won't
                // have our data right away and start with the progress indicator.
                setContentShown(false);
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
        return mListAdapter;
    }

    /**
     * Provide the cursor for the list view.
     */
    public void setListAdapter(ListAdapter listAdapter) {
        boolean hadAdapter = mListAdapter != null;
        mListAdapter = listAdapter;
        ListView contentView = getContentView();
        if (contentView != null) {
            contentView.setAdapter(listAdapter);
            if (!isContentShown() && !hadAdapter) {
                // The list was hidden, and previously didn't have an
                // adapter.  It is now time to show it.
                setContentShown(true);
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
        getContentView().setSelection(position);
    }

    /**
     * Get the position of the currently selected list item.
     */
    public int getSelectedItemPosition() {
        ensure();
        return getContentView().getSelectedItemPosition();
    }

    /**
     * Get the cursor row ID of the currently selected list item.
     */
    public long getSelectedItemId() {
        ensure();
        return getContentView().getSelectedItemId();
    }

    private ListAdapter mListAdapter;

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            ListView contentView = getContentView();
            contentView.focusableViewAvailable(contentView);
        }
    };

    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView)parent, v, position, id);
        }
    };
}
