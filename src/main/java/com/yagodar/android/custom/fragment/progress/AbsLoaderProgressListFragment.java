package com.yagodar.android.custom.fragment.progress;

import android.content.Loader;
import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;

/**
 * Created by yagodar on 18.06.2015.
 */
public abstract class AbsLoaderProgressListFragment extends ProgressListFragment implements ILoaderProgressContext, ILoaderCallback {

    public AbsLoaderProgressListFragment() {
        super();
        mLoaderCallback = new LoaderCallback(this, this, this);
        mLoaderProgressContext = new LoaderProgressContext(this, mLoaderCallback, this);
    }

    @Override
    public void setAvailable(boolean available) {
        mLoaderProgressContext.setAvailable(available);
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        mLoaderProgressContext.startLoading(loaderId, args);
    }

    @Override
    public void startLoading(int loaderId, Bundle args, boolean hidden) {
        mLoaderProgressContext.startLoading(loaderId, args, hidden);
    }

    @Override
    public void finishLoading(int loaderId, boolean hidden) {
        mLoaderProgressContext.finishLoading(loaderId, hidden);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mLoaderCallback.onLoadFinished(loader, loaderResult);
    }

    @Override
    public void onLoaderResult(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mLoaderCallback.onLoaderResult(loader, loaderResult);
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult> loader) {
        mLoaderCallback.onLoaderReset(loader);
    }

    private LoaderProgressContext mLoaderProgressContext;
    private LoaderCallback mLoaderCallback;

}
