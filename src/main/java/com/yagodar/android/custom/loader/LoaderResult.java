package com.yagodar.android.custom.loader;

import android.os.Bundle;

import com.yagodar.essential.operation.OperationArgsResult;
import com.yagodar.essential.operation.OperationResult;

/**
 * Created by yagodar on 17.06.2015.
 */
public class LoaderResult extends OperationArgsResult<Bundle, OperationResult> {

    public LoaderResult() {
        super();
    }

    public LoaderResult(OperationResult data) {
        super(data);
    }

    @Override
    public boolean isSuccessful() {
        OperationResult opResult = getData();
        if(opResult == null) {
            return true;
        }
        return opResult.isSuccessful();
    }

    @Override
    public String getFailMessage() {
        OperationResult opResult = getData();
        if(opResult == null) {
            return null;
        }
        return opResult.getFailMessage();
    }

    @Deprecated
    @Override
    public void setFailMessage(String message) {
        throw new UnsupportedOperationException("Set fail message not supported!");
    }

    @Override
    public Integer getFailMessageId() {
        OperationResult opResult = getData();
        if(opResult == null) {
            return null;
        }
        return opResult.getFailMessageId();
    }

    @Deprecated
    @Override
    public void setFailMessageId(Integer messageId) {
        throw new UnsupportedOperationException("Set fail message id not supported!");
    }

    @Override
    public Throwable getFailThrowable() {
        OperationResult opResult = getData();
        if(opResult == null) {
            return null;
        }
        return opResult.getFailThrowable();
    }

    @Deprecated
    @Override
    public void setFailThrowable(Throwable throwable) {
        throw new UnsupportedOperationException("Set fail throwable not supported!");
    }
}
