package com.yagodar.android.custom.loader;

import android.content.Loader;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yagodar.android.custom.fragment.progress.ILoaderProgressContext;
import com.yagodar.android.custom.fragment.progress.IProgressContext;

import java.util.Set;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderCallback implements ILoaderCallback {
    public LoaderCallback(IProgressContext progressContext, ILoaderCallback srcLoaderCallback, ILoaderProgressContext srcLoaderProgressContext) {
        mProgressContext = progressContext;
        mSrcLoaderCallback = srcLoaderCallback;
        mSrcLoaderProgressContext = srcLoaderProgressContext;
    }

    @Override
    public void onLoaderResult(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mSrcLoaderProgressContext.finishLoading(loader.getId());
    }

    @Override
    public Loader<LoaderResult> onCreateLoader(int id, Bundle args) {
        return mSrcLoaderCallback.onCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        if(loaderResult == null) {
            throw new IllegalArgumentException("Loader Result must not be null!");
        }

        if (!loaderResult.isSuccessful()) {
            Throwable failThrowable = loaderResult.getFailThrowable();

            Set<Integer> hiddenLoaderIdSet = mSrcLoaderProgressContext.getHiddenLoaderIdSet();
            if(!hiddenLoaderIdSet.contains(loader.getId())) {
                String failLoaderResult = null;

                Integer failMessageId = loaderResult.getFailMessageId();
                String failMessage = loaderResult.getFailMessage();

                if (failMessageId == null) {
                    if (failMessage == null) {
                        if (failThrowable == null) {
                            failLoaderResult = "FAIL";
                        } else {
                            failLoaderResult = failThrowable.getMessage();
                        }
                    } else {
                        failLoaderResult = failMessage;
                    }
                } else {
                    try {
                        failLoaderResult = mProgressContext.getActivity().getResources().getString(loaderResult.getFailMessageId());
                    } catch (Resources.NotFoundException ignored) {
                    }

                    if (failLoaderResult == null) {
                        failLoaderResult = "FAIL";
                    }
                }

                Toast.makeText(mProgressContext.getActivity().getApplicationContext(), failLoaderResult, Toast.LENGTH_SHORT).show();
            }

            if(failThrowable != null) {
                Log.e(mProgressContext.getClass().getSimpleName(), failThrowable.getMessage(), failThrowable);
            }
        }

        mSrcLoaderCallback.onLoaderResult(loader, loaderResult);
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult> loader) {}

    private IProgressContext mProgressContext;
    private ILoaderCallback mSrcLoaderCallback;
    private ILoaderProgressContext mSrcLoaderProgressContext;
}
