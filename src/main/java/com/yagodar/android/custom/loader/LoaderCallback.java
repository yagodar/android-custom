package com.yagodar.android.custom.loader;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.yagodar.android.custom.fragment.progress.ILoaderProgressContext;
import com.yagodar.android.custom.fragment.progress.IProgressContext;

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
    public Loader<LoaderResult> onCreateLoader(int id, Bundle args) {
        return mSrcLoaderCallback.onCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(Loader<LoaderResult> loader, LoaderResult result) {
        if(result == null) {
            throw new IllegalArgumentException("Loader Result must not be null!");
        }

        //TODO

        if (!result.isSuccessful()) {
            Throwable failThrowable = result.getFailThrowable();

            //ILoaderProgressContext.ProgressShowType progressShowType = mSrcLoaderProgressContext.getProgressShowType(loader.getId());
            //if(progressShowType != LoaderFactory.ProgressShowType.HIDDEN) {
                String failLoaderResult = null;

                Integer failMessageId = result.getFailMessageId();
                String failMessage = result.getFailMessage();

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
                        failLoaderResult = mProgressContext.getActivity().getResources().getString(result.getFailMessageId());
                    } catch (Resources.NotFoundException ignored) {
                    }

                    if (failLoaderResult == null) {
                        failLoaderResult = "FAIL";
                    }
                }

                Toast.makeText(mProgressContext.getActivity().getApplicationContext(), failLoaderResult, Toast.LENGTH_SHORT).show();
            //}

            if(failThrowable != null) {
                Log.e(mProgressContext.getClass().getSimpleName(), failThrowable.getMessage(), failThrowable);
            }
        }

        mSrcLoaderCallback.onLoaderResult(loader, result);
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult> loader) {}

    @Override
    public void onLoaderResult(Loader<LoaderResult> loader, LoaderResult result) {
        mSrcLoaderProgressContext.finishLoading(loader.getId(), result);
    }

    private IProgressContext mProgressContext;
    private ILoaderCallback mSrcLoaderCallback;
    private ILoaderProgressContext mSrcLoaderProgressContext;
}
