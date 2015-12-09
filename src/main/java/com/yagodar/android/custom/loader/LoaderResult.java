package com.yagodar.android.custom.loader;

import android.os.Bundle;

import com.yagodar.essential.operation.OperationResult;

/**
 * Created by yagodar on 17.06.2015.
 */
public class LoaderResult extends OperationResult<Bundle> {

    public LoaderResult(Bundle loaderArgs) {
        super();
        mLoaderArgs = loaderArgs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" notify=").append(mNotifyDataSet);
        sb.append("}");
        return sb.toString();
    }

    public Bundle getLoaderArgs() {
        return mLoaderArgs;
    }

    public boolean isNotifyDataSet() {
        return mNotifyDataSet;
    }

    public void setNotifyDataSet(boolean needNotifyDataSetChanged) {
        mNotifyDataSet = needNotifyDataSetChanged;
    }

    private boolean mNotifyDataSet;

    private final Bundle mLoaderArgs;
}
