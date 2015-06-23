package com.yagodar.android.custom.loader;

import android.app.Activity;
import android.content.Loader;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yagodar on 23.06.2015.
 */
public class LoaderCallback implements ILoaderCallback {
    public LoaderCallback(Activity activity, ILoaderCallback srcLoaderCallback) {
        mActivity = activity;
        mSrcLoaderCallback = srcLoaderCallback;
    }

    @Override
    public void onLoaderResult(Loader<LoaderResult> loader, LoaderResult loaderResult) {
        mSrcLoaderCallback.onLoaderResult(loader, loaderResult);
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
            String failLoaderResult = null;

            Integer failMessageId = loaderResult.getFailMessageId();
            String failMessage = loaderResult.getFailMessage();
            Throwable failThrowable = loaderResult.getFailThrowable();

            if(failMessageId == null) {
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
                    failLoaderResult = mActivity.getResources().getString(loaderResult.getFailMessageId());
                } catch(Resources.NotFoundException ignored) {}

                if(failLoaderResult == null) {
                    failLoaderResult = "FAIL";
                }
            }

            Toast.makeText(mActivity.getApplicationContext(), failLoaderResult, Toast.LENGTH_SHORT).show();

            if(failThrowable != null) {
                Log.e(mActivity.getClass().getSimpleName(), failThrowable.getMessage(), failThrowable);
            }
        }

        onLoaderResult(loader, loaderResult);
    }

    @Override
    public void onLoaderReset(Loader<LoaderResult> loader) {
        mSrcLoaderCallback.onLoaderReset(loader);
    }

    private Activity mActivity;
    private ILoaderCallback mSrcLoaderCallback;
}
