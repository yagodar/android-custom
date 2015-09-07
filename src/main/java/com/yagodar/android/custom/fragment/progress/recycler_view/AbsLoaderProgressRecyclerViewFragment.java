package com.yagodar.android.custom.fragment.progress.recycler_view;

import android.os.Bundle;
import android.support.v4.content.Loader;

import com.yagodar.android.custom.fragment.progress.ILoaderProgressContext;
import com.yagodar.android.custom.fragment.progress.LoaderProgressContext;
import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;

import java.util.Set;

/**
 * Created by yagodar on 04.09.2015.
 */
public abstract class AbsLoaderProgressRecyclerViewFragment extends ProgressRecyclerViewFragment implements ILoaderProgressContext, ILoaderCallback {

    public AbsLoaderProgressRecyclerViewFragment() {
        super();
        mLoaderCallback = new LoaderCallback(this, this, this);
        mLoaderProgressContext = new LoaderProgressContext(this, mLoaderCallback, this);
    }

    @Override
    public void setAvailable(boolean available) {
        mLoaderProgressContext.setAvailable(available);
    }

    @Override
    public Set<Integer> getHiddenLoaderIdSet() {
        return mLoaderProgressContext.getHiddenLoaderIdSet();
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
