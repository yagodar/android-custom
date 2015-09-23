package com.yagodar.android.custom.fragment.progress;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.yagodar.android.custom.loader.ILoaderCallback;
import com.yagodar.android.custom.loader.LoaderResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderProgressContext implements ILoaderProgressContext {

    public LoaderProgressContext(IProgressContext progressContext, ILoaderCallback loaderCallback, ILoaderProgressContext srcLoaderProgressContext) {
        mProgressContext = progressContext;
        mLoaderCallback = loaderCallback;
        mSrcLoaderProgressContext = srcLoaderProgressContext;
        mProgressShowTypeByLoaderId = new HashMap<>();
    }

    @Override
    public void startLoading(int loaderId, Bundle args, ProgressShowType progressShowType) {
        mProgressShowTypeByLoaderId.put(loaderId, progressShowType);

        boolean setNotAvailable = true;
        switch (progressShowType) {
            case NORMAL:
                setNotAvailable = true;
                break;
            case HIDDEN:
                setNotAvailable = false;
                break;
            default:
                break;
        }
        if(setNotAvailable) {
            mSrcLoaderProgressContext.setAvailable(false);
        }

        mSrcLoaderProgressContext.getLoaderManager().initLoader(loaderId, args, mLoaderCallback);
    }

    @Override
    public void setAvailable(boolean available) {
        mProgressContext.setContentShown(available);
    }

    @Override
    public ProgressShowType getProgressShowType(int loaderId) {
        return mProgressShowTypeByLoaderId.get(loaderId);
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        mSrcLoaderProgressContext.startLoading(loaderId, args, ProgressShowType.NORMAL);
    }

    @Override
    public void finishLoading(int loaderId, LoaderResult loaderResult) {
        mSrcLoaderProgressContext.getLoaderManager().destroyLoader(loaderId);

        boolean setAvailable = true;
        ProgressShowType progressShowType = mProgressShowTypeByLoaderId.get(loaderId);
        switch (progressShowType) {
            case NORMAL:
                setAvailable = true;
                break;
            case HIDDEN:
                setAvailable = false;
                break;
            default:
                break;
        }
        if(setAvailable) {
            mSrcLoaderProgressContext.setAvailable(true);
        }
    }

    @Override
    public Activity getActivity() {
        return mSrcLoaderProgressContext.getActivity();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return mSrcLoaderProgressContext.getLoaderManager();
    }

    private IProgressContext mProgressContext;
    private ILoaderCallback mLoaderCallback;
    private ILoaderProgressContext mSrcLoaderProgressContext;
    private Map<Integer, ProgressShowType> mProgressShowTypeByLoaderId;
}
