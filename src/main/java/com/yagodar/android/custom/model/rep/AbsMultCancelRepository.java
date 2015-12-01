package com.yagodar.android.custom.model.rep;

import android.support.v4.os.CancellationSignal;

import com.yagodar.essential.model.Model;
import com.yagodar.essential.model.rep.IMultRepository;
import com.yagodar.essential.operation.OperationResult;

import java.util.List;
import java.util.Map;

/**
 * Created by yagodar on 30.11.2015.
 */
public abstract class AbsMultCancelRepository<T extends Model> implements IMultRepository<T> {
    @Override
    public OperationResult<T> load(long id) {
        return load(id, null);
    }

    @Override
    public OperationResult<Map<Long, T>> loadAllMap() {
        return loadAllMap(null);
    }

    @Override
    public OperationResult<List<T>> loadAllList() {
        return loadAllList(null);
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
    public OperationResult<Integer> updateAllMap(Map<Long, T> modelById) {
        return updateAllMap(modelById, null);
    }

    @Override
    public OperationResult<Integer> updateAllList(List<T> modelList) {
        return updateAllList(modelList, null);
    }

    @Override
    public OperationResult<Integer> delete(long id) {
        return delete(id, null);
    }

    @Override
    public OperationResult<Integer> delete(T model) {
        return delete(model, null);
    }

    @Override
    public OperationResult<Integer> deleteAll() {
        return deleteAll(null);
    }

    public abstract OperationResult<T> load(long id, CancellationSignal signal);

    public abstract OperationResult<Map<Long,T>> loadAllMap(CancellationSignal signal);

    public abstract OperationResult<List<T>> loadAllList(CancellationSignal signal);

    public abstract OperationResult<Long> insert(CancellationSignal signal);

    public abstract OperationResult<Long> insert(T model, CancellationSignal signal);

    public abstract OperationResult<Integer> update(T model, CancellationSignal signal);

    public abstract OperationResult<Integer> updateAllMap(Map<Long, T> modelById, CancellationSignal signal);

    public abstract OperationResult<Integer> updateAllList(List<T> modelList, CancellationSignal signal);

    public abstract OperationResult<Integer> delete(long id, CancellationSignal signal);

    public abstract OperationResult<Integer> delete(T model, CancellationSignal signal);

    public abstract OperationResult<Integer> deleteAll(CancellationSignal signal);
}
