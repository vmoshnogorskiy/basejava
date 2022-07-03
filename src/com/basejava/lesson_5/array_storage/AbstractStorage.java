package com.basejava.lesson_5.array_storage;

import com.basejava.lesson_5.exception.ExistStorageException;
import com.basejava.lesson_5.exception.NotExistStorageException;
import com.basejava.lesson_5.exception.StorageException;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object key = findKey(r.getUuid());
        if (isExist(key)) {
            doUpdate(r, key);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (isOverflowLimit()) {
            throw new StorageException("ERROR: Резюме не может быть добавлено. Хранилище переполнено", r.getUuid());
        } else if (isExist(findKey(r.getUuid()))) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            return doGet(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            doDelete(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract Object findKey(String uuid);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract boolean isOverflowLimit();

    protected abstract void doSave(Resume r);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract boolean isExist(Object key);
}
