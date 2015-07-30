package com.yagodar.android.custom.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by yagodar on 19.06.2015.
 */
public abstract class AbsAsyncTaskLoader extends AsyncTaskLoader<LoaderResult> {
    public AbsAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    public abstract LoaderResult load();

    @Override
    public LoaderResult loadInBackground() {
        try {
            TimeUnit.MILLISECONDS.sleep(mArgs == null ? 0L : mArgs.getLong(DELAY_START_MILLISECONDS_TAG, 0L));
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return load();
    }

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

    public static String DELAY_START_MILLISECONDS_TAG = "delay";

    private static final String LOG_TAG = AbsAsyncTaskLoader.class.getSimpleName();
}
