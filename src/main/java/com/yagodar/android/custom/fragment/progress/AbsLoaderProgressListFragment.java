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
        mLoaderCallback = new LoaderCallback(this, this);
        mLoaderProgressContext = new LoaderProgressContext(this, mLoaderCallback);
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
