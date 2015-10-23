package com.yagodar.android.custom.loader;

import android.os.Bundle;

import com.yagodar.essential.operation.OperationResult;

/**
 * Created by yagodar on 17.06.2015.
 */
public class LoaderResult extends OperationResult<Bundle> {

    public LoaderResult() {
        super();
    }

    public boolean isNotifyDataSet() {
        return mNotifyDataSet;
    }

    public void setNotifyDataSet(boolean needNotifyDataSetChanged) {
        mNotifyDataSet = needNotifyDataSetChanged;
    }

    private boolean mNotifyDataSet;
}
