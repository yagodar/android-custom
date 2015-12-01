package com.yagodar.android.custom.model.rep;

import android.support.v4.os.CancellationSignal;

import com.yagodar.essential.model.Model;
import com.yagodar.essential.model.rep.IMultGroupRepository;
import com.yagodar.essential.operation.OperationResult;

import java.util.List;
import java.util.Map;

/**
 * Created by yagodar on 30.11.2015.
 */
public abstract class AbsMultGroupCancelRepository<T extends Model> implements IMultGroupRepository<T> {
    @Override
    public OperationResult load(long groupId, long id) {
        return load(groupId, id, null);
    }

    @Override
    public OperationResult<Map<Long,T>> loadGroupMap(long groupId) {
        return loadGroupMap(groupId, null);
    }

    @Override
    public OperationResult<List<T>> loadGroupList(long groupId) {
        return loadGroupList(groupId, null);
    }

    @Override
    public OperationResult<Map<Long, Map<Long,T>>> loadAllMap() {
        return loadAllMap(null);
    }

    @Override
    public OperationResult<Map<Long, List<T>>> loadAllList() {
        return loadAllList(null);
    }

    @Override
    public OperationResult<Long> insert(long groupId) {
        return insert(groupId, (CancellationSignal)null);
    }

    @Override
    public OperationResult<Long> insert(long groupId, T model) {
        return insert(groupId, model, null);
    }

    @Override
    public OperationResult<Integer> update(long groupId, T model) {
        return update(groupId, model, null);
    }

    @Override
    public OperationResult<Integer> updateGroup(long groupId, Map<Long, T> modelById) {
        return updateGroup(groupId, modelById, null);
    }

    @Override
    public OperationResult<Integer> updateGroup(long groupId, List<T> modelList) {
        return updateGroup(groupId, modelList, null);
    }

    @Override
    public OperationResult<Integer> updateAllMap(Map<Long, Map<Long, T>> modelByIdByGroup) {
        return updateAllMap(modelByIdByGroup, null);
    }

    @Override
    public OperationResult<Integer> updateAllList(Map<Long, List<T>> modelListByGroup) {
        return updateAllList(modelListByGroup, null);
    }

    @Override
    public OperationResult<Integer> delete(long groupId, long id) {
        return delete(groupId, id, null);
    }

    @Override
    public OperationResult<Integer> delete(long groupId, T model) {
        return delete(groupId, model, null);
    }

    @Override
    public OperationResult<Integer> deleteGroup(long groupId) {
        return deleteGroup(groupId, null);
    }

    @Override
    public OperationResult<Integer> deleteAll() {
        return deleteAll(null);
    }

    public abstract OperationResult<T> load(long groupId, long id, CancellationSignal signal);

    public abstract OperationResult<Map<Long,T>> loadGroupMap(long groupId, CancellationSignal signal);

    public abstract OperationResult<List<T>> loadGroupList(long groupId, CancellationSignal signal);

    public abstract OperationResult<Map<Long, Map<Long,T>>> loadAllMap(CancellationSignal signal);

    public abstract OperationResult<Map<Long, List<T>>> loadAllList(CancellationSignal signal);

    public abstract OperationResult<Long> insert(long groupId, CancellationSignal signal);

    public abstract OperationResult<Long> insert(long groupId, T model, CancellationSignal signal);

    public abstract OperationResult<Integer> update(long groupId, T model, CancellationSignal signal);

    public abstract OperationResult<Integer> updateGroup(long groupId, Map<Long, T> modelById, CancellationSignal signal);

    public abstract OperationResult<Integer> updateGroup(long groupId, List<T> modelList, CancellationSignal signal);

    public abstract OperationResult<Integer> updateAllMap(Map<Long, Map<Long, T>> modelByIdByGroup, CancellationSignal signal);

    public abstract OperationResult<Integer> updateAllList(Map<Long, List<T>> modelListByGroup, CancellationSignal signal);

    public abstract OperationResult<Integer> delete(long groupId, long id, CancellationSignal signal);

    public abstract OperationResult<Integer> delete(long groupId, T model, CancellationSignal signal);

    public abstract OperationResult<Integer> deleteGroup(long groupId, CancellationSignal signal);

    public abstract OperationResult<Integer> deleteAll(CancellationSignal signal);
}
