package com.yagodar.android.custom.fragment.progress;

import android.app.Activity;
import android.app.LoaderManager;
import android.os.Bundle;

import com.yagodar.android.custom.loader.AbsAsyncTaskLoader;
import com.yagodar.android.custom.loader.ILoaderCallback;

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
    public void setAvailable(boolean available) {
        mProgressContext.setContentShown(available);
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        mSrcLoaderProgressContext.startLoading(loaderId, args, false);
    }

    @Override
    public void startLoading(int loaderId, Bundle args, boolean hidden) {
        if(!hidden) {
            mSrcLoaderProgressContext.setAvailable(false);
        }
        if(args == null) {
            args = new Bundle();
        }
        args.putBoolean(AbsAsyncTaskLoader.HIDDEN_LOADER_TAG, hidden);
        mSrcLoaderProgressContext.getLoaderManager().initLoader(loaderId, args, mLoaderCallback);
    }

    @Override
    public void finishLoading(int loaderId, boolean hidden) {
        try {
            mSrcLoaderProgressContext.getLoaderManager().destroyLoader(loaderId);
        } catch(Exception e) {
            System.out.print("sdfsdf");
        }

        if(!hidden) {
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
}
