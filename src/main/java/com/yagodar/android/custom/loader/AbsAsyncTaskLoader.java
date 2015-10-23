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
     * <p>Take care of loading data. Result of {@link #startLoading()}.</p>
     * <p>Called in two cases (as of <b>android.support.v4-23.1.0</b>):</p>
     * <ul>
     * <li>Start <b>new</b> loader</li>
     * <li>Start <b>retained</b> loader after been stop</li>
     * </ul>
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

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
    }








    @Override
    public void onCanceled(LoaderResult data) {
        super.onCanceled(data);
    }

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
    public void onContentChanged() {
        super.onContentChanged();
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
}
