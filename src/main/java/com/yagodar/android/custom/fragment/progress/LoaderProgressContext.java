package com.yagodar.android.custom.fragment.progress;

import android.app.Activity;
import android.app.LoaderManager;
import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderCallback;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderProgressContext implements ILoaderProgressContext {

    public LoaderProgressContext(IProgressContext progressContext, ILoaderCallback loaderCallback, ILoaderProgressContext srcLoaderProgressContext) {
        mProgressContext = progressContext;
        mLoaderCallback = loaderCallback;
        mSrcLoaderProgressContext = srcLoaderProgressContext;
        mHiddenLoaderIdSet = new HashSet<>();
    }

    @Override
    public void startLoading(int loaderId, Bundle args, boolean hidden) {
        if(hidden) {
            mHiddenLoaderIdSet.add(loaderId);
        } else {
            mSrcLoaderProgressContext.setAvailable(false);
        }
        mSrcLoaderProgressContext.getLoaderManager().initLoader(loaderId, args, mLoaderCallback);
    }

    @Override
    public void setAvailable(boolean available) {
        mProgressContext.setContentShown(available);
    }

    @Override
    public Set<Integer> getHiddenLoaderIdSet() {
        return mHiddenLoaderIdSet;
    }

    @Override
    public void startLoading(int loaderId, Bundle args) {
        mSrcLoaderProgressContext.startLoading(loaderId, args, false);
    }

    @Override
    public void finishLoading(int loaderId) {
        try {
            mSrcLoaderProgressContext.getLoaderManager().destroyLoader(loaderId);
        } catch(Exception e) {
            System.out.print("sdfsdf"); //TODO del
        }
        if(mHiddenLoaderIdSet.contains(loaderId)) {
            mHiddenLoaderIdSet.remove(loaderId);
        } else {
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
    private Set<Integer> mHiddenLoaderIdSet;
}
