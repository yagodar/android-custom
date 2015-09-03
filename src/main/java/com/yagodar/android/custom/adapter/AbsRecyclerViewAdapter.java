package com.yagodar.android.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yagodar.essential.model.ListModel;
import com.yagodar.essential.model.Model;

/**
 * Created by yagodar on 04.09.2015.
 */
public abstract class AbsRecyclerViewAdapter<M extends Model, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    public AbsRecyclerViewAdapter(Context context, ListModel<M> listModel) {
        mLayoutInflater = LayoutInflater.from(context);
        //setHasStableIds(true); TODO WTF!!
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
    public abstract H onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(H viewHolder, int i);

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

    private final LayoutInflater mLayoutInflater;
    private ListModel<M> mListModel;
}
