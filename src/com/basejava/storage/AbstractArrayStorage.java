package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        int key = findKey(r.getUuid());
        if (isOverflowLimit()) {
            throw new StorageException("ERROR: Резюме не может быть добавлено. Хранилище переполнено", r.getUuid());
        } else if (isExist(key)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r, key);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    protected boolean isOverflowLimit() {
        return STORAGE_LIMIT <= size;
    }

    protected abstract Integer findKey(String uuid);

    protected abstract void doSave(Resume r, int index);
}