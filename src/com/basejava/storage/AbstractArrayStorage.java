package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void doSave(Resume r, Integer key) {
        if (isOverflowLimit()) {
            throw new StorageException("ERROR: Резюме не может быть добавлено. Хранилище переполнено", r.getUuid());
        } else {
            insertResume(r, key);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage[key] = resume;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    protected boolean isOverflowLimit() {
        return STORAGE_LIMIT <= size;
    }

    protected abstract void insertResume(Resume r, Integer key);
}