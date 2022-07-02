package com.basejava.lesson_5.array_storage;

import java.util.Arrays;

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
    protected void doUpdate(Resume resume, Object key) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected boolean isOverflowLimit() {
        return STORAGE_LIMIT <= size;
    }

    protected abstract Integer findKey(String uuid);

    protected abstract void doSave(Resume r);
}