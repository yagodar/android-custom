package com.yagodar.android.custom.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by yagodar on 19.06.2015.
 */
public abstract class AbsAsyncTaskLoader extends AsyncTaskLoader<LoaderResult> {
    public AbsAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    /**
     * <p>Take care of loading data. Result of {@link #startLoading()}.</p>
     * <p>Called in two cases (as of <b>android.support.v4-23.1.0</b>):</p>
     * <ul>
     * <li>Start <b>new</b> loader</li>
     * <li>Start <b>retained</b> loader after been stop</li>
     * </ul>
     */
    @Override
    protected void onStartLoading() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onStartLoading", new Exception());
        }
        if (mLoaderResult == null) {
            forceLoad();
        } else {
            deliverResult(mLoaderResult);
        }
    }

    @Override
    protected LoaderResult onLoadInBackground() {
        return super.onLoadInBackground();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
    }

    @Override
    public void deliverResult(LoaderResult data) {
        mLoaderResult = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }





    @Override
    public void onCanceled(LoaderResult data) {
        super.onCanceled(data);
    }

    public abstract LoaderResult load();

    @Override
    public LoaderResult loadInBackground() {
        /*try {
            TimeUnit.MILLISECONDS.sleep(mArgs == null ? 0L : mArgs.getLong(DELAY_START_MILLISECONDS_TAG, 0L));
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage(), e);
        }*/ //TODO updateThrottle
        return load();
    }





    @Override
    public void deliverCancellation() {
        super.deliverCancellation();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }





    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onStopLoading() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onStopLoading", new Exception());
        }
        //cancelLoad();
        super.onStopLoading();
    }



    @Override
    protected void onReset() {
        //onStopLoading();
        //mLoaderResult = null;
        super.onReset();
    }

















    protected Bundle getArgs() {
        return mArgs;
    }

    private Bundle mArgs;
    private LoaderResult mLoaderResult;

    private static final boolean DEBUG = true;
    public static final String TAG = AbsAsyncTaskLoader.class.getSimpleName();

    public static String DELAY_START_MILLISECONDS_TAG = "delay";
}
