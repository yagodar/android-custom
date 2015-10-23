package com.yagodar.android.custom.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yagodar on 19.06.2015.
 */
public abstract class AbsAsyncTaskLoader extends AsyncTaskLoader<LoaderResult> {
    public AbsAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
    }

    /**
     * <p>Take care of loading data. Result of a call to {@link #startLoading()}.</p>
     * <p>Valid for <b>android.support.v4-23.0.1</b></p>
     * <p>Called in two cases:</p>
     * <ul>
     * <li>Loader <b>retained</b> on activity restart via onStart()</li>
     * <p>{@link android.support.v4.app.Fragment#onStart() Fragment.onStart()} -></p>
     * <p><i>or</i></p>
     * <p>{@link android.support.v4.app.FragmentActivity#onStart() FragmentActivity.onStart()}
     * -> {@link android.support.v4.app.FragmentController#doLoaderStart() FragmentController.doLoaderStart()} -></p>
     * <p>-> {@link android.support.v4.app.FragmentHostCallback#doLoaderStart() FragmentHostCallback.doLoaderStart()} -></p>
     * <p><i>then</i></p>
     * <p>-> {@link android.support.v4.app.LoaderManagerImpl#doStart() LoaderManagerImpl.doStart()} ->
     * <li>Install <b>new</b> loader</li>
     * (also pending loader after cancel or complete old loader)
     * <p>{@link android.support.v4.app.LoaderManagerImpl#installLoader(android.support.v4.app.LoaderManagerImpl.LoaderInfo) LoaderManagerImpl.installLoader(LoaderInfo)} -></p>
     * </ul>
     * <p><i>then</i></p>
     * <p>-> {@link android.support.v4.app.LoaderManagerImpl.LoaderInfo#start() LoaderInfo.start()} ->
     * {@link #startLoading()}</p>
     * <p><b>Caution!</b></p>
     * <p>FragmentHostCallback has collection of all LoaderManagers
     * {@link android.support.v4.app.FragmentHostCallback#mAllLoaderManagers FragmentHostCallback.mAllLoaderManagers},
     * and when activity stop, it removes all not retaining LoaderManagers from collection  and also destroy its Loaders.
     * Seams like it`s <a href="http://stackoverflow.com/questions/15897547/loader-unable-to-retain-itself-during-certain-configuration-change">bag</a>
     * (<a href="https://code.google.com/p/android/issues/detail?id=183783">issue</a>)
     * - LoaderManager unable to retain. But, Fragment also have link to its LoaderManager,
     * and if Fragment retained - link also retained. And <i>after second</i> activity stop there is no LoaderManagers
     * in collection, nothing to destroy, but there is link in Fragment. And retained LoaderManager works.</p>
     */
    @Override
    protected void onStartLoading() {
        f(LOG_TAG, toString() + " onStartLoading()", true);

        if (mLoaderResult != null) {
            deliverResult(mLoaderResult);
        } else {
            forceLoad();
        }
    }

    public static void f(String logTag, String msg, boolean printStackTrace) {
        File file = new File(LOG_FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append("[" + new SimpleDateFormat("HH:mm:ss:SSS").format(new Date()) + "]\n[" + logTag + "]\n" + msg + "\n");

            if(printStackTrace) {
                bw.append(getStackTraceStr());
                bw.append("\n");
            }

            bw.append("\n");

            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStackTraceStr() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        String stackTraceOut = "\tStack trace:\n";
        if(stackTrace != null && stackTrace.length > 0) {
            for (int i = 4; i < stackTrace.length; i++) {
                stackTraceOut += "\tline [" +  stackTrace[i].getLineNumber() + "]\t" + stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + " <- \n";
            }

            stackTraceOut = stackTraceOut.substring(0, stackTraceOut.length() - 5);
        }
        else {
            stackTraceOut += "[empty]";
        }

        return stackTraceOut;
    }

    private final static String LOG_FILE_PATH = "sdcard/" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + ".log";


    public abstract LoaderResult load();

    @Override
    public LoaderResult loadInBackground() {
        /*try {
            TimeUnit.MILLISECONDS.sleep(mArgs == null ? 0L : mArgs.getLong(DELAY_START_MILLISECONDS_TAG, 0L));
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }*/ //TODO updateThrottle
        return load();
    }



    @Override
    public void deliverResult(LoaderResult data) {
        mLoaderResult = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public void deliverCancellation() {
        super.deliverCancellation();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    public void onCanceled(LoaderResult data) {
        super.onCanceled(data);
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    public void onStopLoading() {
        //cancelLoad();
        super.onStopLoading();

        f(LOG_TAG, toString() + " onStopLoading()", true);
    }



    @Override
    protected void onReset() {
        //onStopLoading();
        //mLoaderResult = null;
        super.onReset();
    }

    protected Bundle getArgs() {
        return mArgs;
    }

    private Bundle mArgs;
    private LoaderResult mLoaderResult;

    public static String DELAY_START_MILLISECONDS_TAG = "delay";

    private static final String LOG_TAG = AbsAsyncTaskLoader.class.getSimpleName();
}
