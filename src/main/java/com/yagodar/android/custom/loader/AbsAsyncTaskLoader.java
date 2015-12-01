package com.yagodar.android.custom.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;
import android.util.Log;

import com.yagodar.essential.ForStackTraceException;

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

    @Override
    public void onCanceled(LoaderResult data) {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onCanceled: data=" + data, new ForStackTraceException());
        }
        mLoaderResult = data;
    }

    @Override
    public LoaderResult loadInBackground() {
        if (DEBUG) {
            Log.d(TAG, this + " >>> loadInBackground", new ForStackTraceException());
        }
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                OperationCanceledException e = new OperationCanceledException();
                if (DEBUG) {
                    Log.d(TAG, this + " >>> loadInBackground throw OperationCanceledException", e);
                }
                throw e;
            }
            if (mLoaderResult != null) {
                if (DEBUG) {
                    Log.d(TAG, this + " >>> loadInBackground return existing mLoaderResult: " + mLoaderResult, new ForStackTraceException());
                }
                return mLoaderResult;
            }
            if (DEBUG) {
                Log.d(TAG, this + " >>> loadInBackground mCancellationSignal -> new", new ForStackTraceException());
            }
            mCancellationSignal = new CancellationSignal();
        }

        try {
            return load(mCancellationSignal);
        } catch (OperationCanceledException e) {
            if (DEBUG) {
                Log.d(TAG, this + " >>> loadInBackground catch OperationCanceledException", e);
            }
            throw e;
        } finally {
            synchronized (this) {
                if (DEBUG) {
                    Log.d(TAG, this + " >>> loadInBackground finally mCancellationSignal -> null", new ForStackTraceException());
                }
                mCancellationSignal = null;
            }
        }
    }

    @Override
    public void cancelLoadInBackground() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> cancelLoadInBackground", new ForStackTraceException());
        }
        super.cancelLoadInBackground();
        synchronized (this) {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        }
    }

    /**
     * <p>Take care of loading data as per {@link #startLoading()}.</p>
     * <p>Called in cases:</p>
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
     * <p>Called in cases:</p>
     * <ul>
     * <li>Associated fragment/activity is being really stopped (not config change).</li>
     * </ul>
     */
    @Override
    protected void onStopLoading() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onStopLoading", new ForStackTraceException());
        }
        cancelLoad();
    }

    @Override
    protected void onAbandon() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onAbandon", new ForStackTraceException());
        }
        super.onAbandon();
    }

    @Override
    protected void onReset() {
        if(DEBUG) {
            Log.d(TAG, this + " >>> onReset", new ForStackTraceException());
        }
        onStopLoading();
        mLoaderResult = null;
    }

    public abstract LoaderResult load(CancellationSignal signal);

    protected Bundle getArgs() {
        return mArgs;
    }

    private Bundle mArgs;
    private LoaderResult mLoaderResult;
    private CancellationSignal mCancellationSignal;

    private static final boolean DEBUG = false;

    public static final String TAG = AbsAsyncTaskLoader.class.getSimpleName();
}
