package com.yagodar.android.custom.fragment.progress;

import android.content.Loader;
import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;


/**
 * Created by yagodar on 18.06.2015.
 */
public abstract class AbsLoaderProgressFragment extends ProgressFragment implements ILoaderProgressContext, ILoaderCallback {

    public AbsLoaderProgressFragment() {
        super();
        mLoaderCallback = new LoaderCallback(this, this);
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
    public void finishLoading(int loaderId) {
        mLoaderProgressContext.finishLoading(loaderId);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mLoaderCallback.onLoadFinished(loader, loaderResult);
    }

    private LoaderProgressContext mLoaderProgressContext;
    private LoaderCallback mLoaderCallback;
}
