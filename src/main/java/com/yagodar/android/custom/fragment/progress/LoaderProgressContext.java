package com.yagodar.android.custom.fragment.progress;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderProgressContext implements ILoaderProgressContext {

    public LoaderProgressContext(IProgressContext progressContext, ILoaderCallback loaderCallback, ILoaderProgressContext srcLoaderProgressContext) {
        mProgressContext = progressContext;
        mLoaderCallback = loaderCallback;
        mSrcLoaderProgressContext = srcLoaderProgressContext;
    }

    @Override
    public Activity getActivity() {
        return mSrcLoaderProgressContext.getActivity();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return mSrcLoaderProgressContext.getLoaderManager();
    }

    @Override
    public void startLoading(int id, Bundle args) {
        Loader<LoaderResult> loader = mSrcLoaderProgressContext.getLoaderManager().getLoader(id);
        if(loader == null) {
            mSrcLoaderProgressContext.onStartLoading(id, args);
        }
        mSrcLoaderProgressContext.getLoaderManager().initLoader(id, args, mLoaderCallback);
    }

    @Override
    public void finishLoading(int id, LoaderResult result) {
        mSrcLoaderProgressContext.getLoaderManager().destroyLoader(id);
        mSrcLoaderProgressContext.onFinishLoading(id, result);
    }

    @Override
    public void onStartLoading(int id, Bundle args) {
        mProgressContext.setContentShown(false);
    }

    @Override
    public void onFinishLoading(int id, LoaderResult result) {
        mProgressContext.setContentShown(true);
    }

    private IProgressContext mProgressContext;
    private ILoaderCallback mLoaderCallback;
    private ILoaderProgressContext mSrcLoaderProgressContext;
}
