package com.yagodar.android.custom.loader;

import android.os.Bundle;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderContext extends ILoaderManagerContext {

    void startLoading(int id, Bundle args);

    void finishLoading(int id, LoaderResult result);

}
