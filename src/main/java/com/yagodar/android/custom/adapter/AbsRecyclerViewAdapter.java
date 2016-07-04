package com.yagodar.android.custom.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.yagodar.essential.model.ListModel;
import com.yagodar.essential.model.Model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by yagodar on 04.09.2015.
 */
public abstract class AbsRecyclerViewAdapter<M extends Model, H extends AbsRecyclerViewAdapter.AbsViewHolder<M>> extends RecyclerView.Adapter<H> {
    public AbsRecyclerViewAdapter(Context context,
                                  View.OnClickListener onClickListener,
                                  View.OnLongClickListener onLongClickListener,
                                  ListModel<M> listModel) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnClickListener = onClickListener;
        mOnLongClickListener = onLongClickListener;
        mSelectionManager = new SelectionManager();
        setHasStableIds(true);
        setListModel(listModel);
    }

    @Override
    public int getItemCount() {
        if(mListModel != null && mListModel.isLoaded()) {
            return mListModel.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return mListModel.getModel(position).getId();
    }

    @Override
    public void onBindViewHolder(H viewHolder, int position) {
        viewHolder.onBind(
                getItem(position),
                position
        );
        ((Checkable) viewHolder.itemView).setChecked(
                getSelectionManager().mSelectedItemPositions.contains(position)
        );
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(createItemView(parent, viewType), mOnClickListener, mOnLongClickListener);
    }

    public M getItem(int position) {
        return mListModel.getModel(position);
    }

    public SelectionManager getSelectionManager() {
        return mSelectionManager;
    }

    protected abstract H onCreateViewHolder(View itemView, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener);

    protected abstract View createItemView(ViewGroup parent, int viewType);

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    protected ListModel<M> getListModel() {
        return mListModel;
    }

    protected void setListModel(ListModel<M> listModel) {
        if(listModel == null) {
            throw new IllegalArgumentException("List Model must not be null!");
        }
        if(mListModel == null) {
            mListModel = listModel;
        }
    }

    public class SelectionManager {
        public SelectionManager() {
            mSelectedItemPositions = new HashSet<>();
        }

        public void toggleItemSelection(int position) {
            if(!mSelectedItemPositions.add(position)) {
                mSelectedItemPositions.remove(position);
            }
            notifyItemChanged(position);
        }

        public void clearItemSelections() {
            Iterator<Integer> iterator = mSelectedItemPositions.iterator();
            int position;
            while(iterator.hasNext()) {
                position = iterator.next();
                iterator.remove();
                notifyItemChanged(position);
            }
        }

        public int getSelectedItemCount() {
            return mSelectedItemPositions.size();
        }

        public Set<Integer> getSelectedItemPositions() {
            return mSelectedItemPositions;
        }

        public Parcelable onSaveInstanceState() {
            SelectionSavedState selectionState = new SelectionSavedState();
            if (getSelectedItemCount() > 0) {
                selectionState.saveSelectedItemPositions(mSelectedItemPositions);
            }
            return selectionState;
        }

        public void onRestoreInstanceState(Parcelable state) {
            if (state instanceof SelectionSavedState) {
                SelectionSavedState selectionState = (SelectionSavedState) state;
                if(selectionState.mSelectedItemPositions != null) {
                    for (int position : selectionState.mSelectedItemPositions) {
                        toggleItemSelection(position);
                    }
                }
            } else {
                throw new IllegalArgumentException("Content item view must be Checkable for support selection behavior!");
            }
        }

        private final Set<Integer> mSelectedItemPositions;
    }

    public static class SelectionSavedState implements Parcelable {
        public SelectionSavedState() {}

        public SelectionSavedState(Parcel in) {
            mSelectedItemPositions = in.createIntArray();
        }

        public SelectionSavedState(SelectionSavedState other) {
            mSelectedItemPositions = other.mSelectedItemPositions;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeIntArray(mSelectedItemPositions);
        }

        private void saveSelectedItemPositions(Set<Integer> positions) {
            mSelectedItemPositions = new int[positions.size()];
            int i = 0;
            for (int position : positions) {
                mSelectedItemPositions[i++] = position;
            }
        }

        private int[] mSelectedItemPositions;

        public static final Parcelable.Creator<SelectionSavedState> CREATOR = new Parcelable.Creator<SelectionSavedState>() {
            @Override
            public SelectionSavedState createFromParcel(Parcel in) {
                return new SelectionSavedState(in);
            }

            @Override
            public SelectionSavedState[] newArray(int size) {
                return new SelectionSavedState[size];
            }
        };
    }

    public static abstract class AbsViewHolder<M> extends RecyclerView.ViewHolder {
        public AbsViewHolder(View itemView) {
            super(itemView);
            if(!(itemView instanceof Checkable)) {
                throw new IllegalArgumentException("Item view must be Checkable for support selection behavior!");
            }
        }

        public abstract void onBind(M model, int position);
    }

    private final LayoutInflater mLayoutInflater;
    private final View.OnClickListener mOnClickListener;
    private final View.OnLongClickListener mOnLongClickListener;
    private final SelectionManager mSelectionManager;
    private ListModel<M> mListModel;
}
