package com.yagodar.android.custom.fragment.progress;

import android.os.Bundle;

import com.yagodar.android.custom.loader.ILoaderContext;

import java.util.Set;

/**
 * Created by yagodar on 23.06.2015.
 */
public interface ILoaderProgressContext extends ILoaderContext {

    void startLoading(int loaderId, Bundle args, boolean hidden);

    void setAvailable(boolean available);

    Set<Integer> getHiddenLoaderIdSet();
}
