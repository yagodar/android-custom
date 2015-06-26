package com.yagodar.android.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.yagodar.essential.model.ListModel;
import com.yagodar.essential.model.Model;

/**
 * Created by yagodar on 24.06.2015.
 */
public abstract class AbsListAdapter<T extends Model> extends BaseAdapter {
    public AbsListAdapter(Context context, View.OnClickListener onClickListener, ListModel<T> listModel) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnClickListener = onClickListener;
        setListModel(listModel);
    }

    @Override
    public int getCount() {
        if(mListModel != null && mListModel.isLoaded()) {
            return mListModel.getCount();
        }

        return 0;
    }

    @Override
    public T getItem(int position) {
        return mListModel.getModel(position);
    }

    @Override
    public long getItemId(int position) {
        return mListModel.getModel(position).getId();
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    protected View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    protected void setListModel(ListModel<T> listModel) {
        if(listModel == null) {
            throw new IllegalArgumentException("List Model must not be null!");
        }

        if(mListModel == null) {
            mListModel = listModel;
        }
    }

    protected ListModel<T> getListModel() {
        return mListModel;
    }

    private final LayoutInflater mLayoutInflater;
    private final View.OnClickListener mOnClickListener;
    private ListModel<T> mListModel;
}
