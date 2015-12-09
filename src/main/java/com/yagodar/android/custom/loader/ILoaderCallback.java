package com.yagodar.android.custom.loader;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderCallback extends LoaderManager.LoaderCallbacks<LoaderResult> {

    void onLoaderResult(Loader<LoaderResult> loader, LoaderResult result);

}
