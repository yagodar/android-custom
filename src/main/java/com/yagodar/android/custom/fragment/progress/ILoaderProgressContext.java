package com.yagodar.android.custom.fragment.progress;

import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderContext;
import com.yagodar.android.custom.loader.LoaderResult;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderProgressContext extends ILoaderContext {

    void onStartLoading(int id, Bundle args);

    void onFinishLoading(int id, LoaderResult result);

}
