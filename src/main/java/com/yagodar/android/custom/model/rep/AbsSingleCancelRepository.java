package com.yagodar.android.custom.model.rep;

import android.support.v4.os.CancellationSignal;

import com.yagodar.essential.model.Model;
import com.yagodar.essential.model.rep.ISingleRepository;
import com.yagodar.essential.operation.OperationResult;

/**
 * Created by yagodar on 30.11.2015.
 */
public abstract class AbsSingleCancelRepository<T extends Model> implements ISingleRepository<T> {
    @Override
    public OperationResult<T> load() {
        return load(null);
    }

    @Override
    public OperationResult<Long> insert() {
        return insert((CancellationSignal)null);
    }

    @Override
    public OperationResult<Long> insert(T model) {
        return insert(model, null);
    }

    @Override
    public OperationResult<Integer> update(T model) {
        return update(model, null);
    }

    @Override
    public OperationResult<Integer> delete() {
        return delete(null);
    }

    public abstract OperationResult<T> load(CancellationSignal signal);

    public abstract OperationResult<Long> insert(CancellationSignal signal);

    public abstract OperationResult<Long> insert(T model, CancellationSignal signal);

    public abstract OperationResult<Integer> update(T model, CancellationSignal signal);

    public abstract OperationResult<Integer> delete(CancellationSignal signal);
}
