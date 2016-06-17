package com.yagodar.android.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yagodar.essential.model.ListModel;
import com.yagodar.essential.model.Model;

/**
 * Created by yagodar on 04.09.2015.
 */
public abstract class AbsRecyclerViewAdapter<M extends Model, H extends AbsRecyclerViewAdapter.AbsViewHolder<M>> extends RecyclerView.Adapter<H> {

    public AbsRecyclerViewAdapter(Context context, View.OnClickListener onClickListener, ListModel<M> listModel) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnClickListener = onClickListener;
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
        viewHolder.onBind(getItem(position), position);
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(createItemView(parent, viewType), mOnClickListener);
    }

    public M getItem(int position) {
        return mListModel.getModel(position);
    }

    protected abstract H onCreateViewHolder(View itemView, View.OnClickListener onClickListener);

    protected abstract View createItemView(ViewGroup parent, int viewType);

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    protected void setListModel(ListModel<M> listModel) {
        if(listModel == null) {
            throw new IllegalArgumentException("List Model must not be null!");
        }

        if(mListModel == null) {
            mListModel = listModel;
        }
    }

    protected ListModel<M> getListModel() {
        return mListModel;
    }

    public static abstract class AbsViewHolder<M> extends RecyclerView.ViewHolder {

        public AbsViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBind(M model, int position);
    }

    private final LayoutInflater mLayoutInflater;
    private final View.OnClickListener mOnClickListener;
    private ListModel<M> mListModel;
}
