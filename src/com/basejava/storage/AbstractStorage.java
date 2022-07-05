package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object key = findExistingKey(r.getUuid());
        doUpdate(r, key);
    }

    @Override
    public void save(Resume r) {
        findNotExistingKey(r.getUuid());
        doSave(r);
    }

    @Override
    public Resume get(String uuid) {
        Object key = findExistingKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = findExistingKey(uuid);
        doDelete(key);
    }

    protected Object findExistingKey(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            return key;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void findNotExistingKey(String uuid) {
        if (isExist(findKey(uuid))) {
            throw new ExistStorageException(uuid);
        }
    }

    protected abstract Object findKey(String uuid);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract boolean isExist(Object key);
}
