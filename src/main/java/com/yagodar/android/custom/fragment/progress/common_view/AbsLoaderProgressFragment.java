package com.yagodar.android.custom.fragment.progress.common_view;

import android.os.Bundle;
import android.support.v4.content.Loader;

import com.yagodar.android.custom.fragment.progress.ILoaderProgressContext;
import com.yagodar.android.custom.fragment.progress.LoaderProgressContext;
import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;

/**
 * Created by yagodar on 18.06.2015.
 */
public abstract class AbsLoaderProgressFragment extends ProgressFragment implements ILoaderProgressContext, ILoaderCallback {

    public AbsLoaderProgressFragment() {
        super();
        mLoaderCallback = new LoaderCallback(this, this, this);
        mLoaderProgressContext = new LoaderProgressContext(this, mLoaderCallback, this);
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        mLoaderProgressContext.startLoading(loaderId, args);
    }

    @Override
    public void finishLoading(int loaderId, LoaderResult loaderResult) {
        mLoaderProgressContext.finishLoading(loaderId, loaderResult);
    }

    @Override
    public void startLoading(int loaderId, Bundle args, ProgressShowType progressShowType) {
        mLoaderProgressContext.startLoading(loaderId, args, progressShowType);
    }

    @Override
    public void setAvailable(boolean available) {
        mLoaderProgressContext.setAvailable(available);
    }

    @Override
    public ProgressShowType getProgressShowType(int loaderId) {
        return mLoaderProgressContext.getProgressShowType(loaderId);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mLoaderCallback.onLoadFinished(loader, loaderResult);
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult> loader) {
        mLoaderCallback.onLoaderReset(loader);
    }

    @Override
    public void onLoaderResult(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mLoaderCallback.onLoaderResult(loader, loaderResult);
    }

    private LoaderProgressContext mLoaderProgressContext;
    private LoaderCallback mLoaderCallback;
}
