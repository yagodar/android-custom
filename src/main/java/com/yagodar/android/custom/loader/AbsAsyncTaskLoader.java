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

    @Override
    public void deliverResult(LoaderResult data) {
        if(DEBUG) {
            Log.d(TAG, this + " >>> deliverResult: data=" + data, new ForStackTraceException());
        }
        mLoaderResult = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    /**
     * <p>Take care of loading data as per {@link #startLoading()}.</p>
     * <p>Called in cases (<b>android.support.v4-23.1.0</b>):</p>
     * <ul>
     * <li>Start <b>new</b> loader</li>
     * <li>Start <b>retained</b> loader after being stopped</li>
     * </ul>
     */
    @Override
    protected void onStartLoading() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onStartLoading", new ForStackTraceException());
        }
        if (mLoaderResult == null) {
            forceLoad();
        } else {
            deliverResult(mLoaderResult);
        }
    }

    /**
     * <p>Take care of stopping loader as per {@link #stopLoading()}.</p>
     * <p>Stops delivery of updates.</p>
     * <p>Called in cases (<b>android.support.v4-23.1.0</b>):</p>
     * <ul>
     * <li>Associated fragment/activity is being really stopped (not config change).</li>
     * </ul>
     */
    @Override
    protected void onStopLoading() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onStopLoading", new ForStackTraceException());
        }
    }

    /**
     * <p>Take care of resetting loader as per {@link #reset()}.</p>
     * <p>Resets the state of the Loader.</p>
     */
    @Override
    protected void onReset() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onReset", new ForStackTraceException());
        }
        mLoaderResult = null;
    }


    @Override
    public void onCanceled(LoaderResult data) {
        super.onCanceled(data);
    }

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
    protected LoaderResult onLoadInBackground() {
        return super.onLoadInBackground();
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
    }


    public abstract LoaderResult load();

    protected Bundle getArgs() {
        return mArgs;
    }


    private static class ForStackTraceException extends Exception {}

    private Bundle mArgs;
    private LoaderResult mLoaderResult;

    private static final boolean DEBUG = true;
    public static final String TAG = AbsAsyncTaskLoader.class.getSimpleName();

    public static String DELAY_START_MILLISECONDS_TAG = "delay";
}
