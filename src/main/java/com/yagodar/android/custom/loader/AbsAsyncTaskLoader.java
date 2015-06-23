package com.yagodar.android.custom.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by yagodar on 19.06.2015.
 */
public abstract class AbsAsyncTaskLoader extends AsyncTaskLoader<LoaderResult> {
    public AbsAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    @Override
    public abstract LoaderResult loadInBackground();

    @Override
    public void deliverResult(LoaderResult data) {
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mData = null;
    }

    protected Bundle getArgs() {
        return mArgs;
    }

    private Bundle mArgs;
    private LoaderResult mData;
}
