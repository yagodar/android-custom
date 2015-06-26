package com.yagodar.android.custom.loader;

import android.app.Activity;
import android.app.LoaderManager;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderManagerContext {

    Activity getActivity();

    LoaderManager getLoaderManager();

}
