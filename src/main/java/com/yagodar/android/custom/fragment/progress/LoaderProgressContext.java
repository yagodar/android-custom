package com.yagodar.android.custom.fragment.progress;

import android.app.Activity;
import android.app.LoaderManager;
import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderCallback;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderProgressContext implements ILoaderProgressContext {

    public LoaderProgressContext(IProgressContext progressContext, ILoaderCallback loaderCallback) {
        mProgressContext = progressContext;
        mLoaderCallback = loaderCallback;
    }

    @Override
    public void setAvailable(boolean available) {
        mProgressContext.setContentShown(available);
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        setAvailable(false);
        getLoaderManager().initLoader(loaderId, args, mLoaderCallback);
    }

    @Override
    public void finishLoading(int loaderId) {
        getLoaderManager().destroyLoader(loaderId);
        setAvailable(true);
    }

    @Override
    public Activity getActivity() {
        return mProgressContext.getActivity();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return mProgressContext.getLoaderManager();
    }

    private IProgressContext mProgressContext;
    private ILoaderCallback mLoaderCallback;
}
