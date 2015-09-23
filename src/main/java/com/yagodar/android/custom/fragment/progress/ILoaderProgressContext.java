package com.yagodar.android.custom.fragment.progress;

import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderContext;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderProgressContext extends ILoaderContext {

    void startLoading(int loaderId, Bundle args, ProgressShowType progressShowType);

    void setAvailable(boolean available);

    ProgressShowType getProgressShowType(int loaderId);

    enum ProgressShowType {
        NORMAL,
        HIDDEN
    }
}
