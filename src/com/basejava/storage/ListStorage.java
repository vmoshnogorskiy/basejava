package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();
    protected int size = 0;

    @Override
    protected Object findKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.set((Integer) key, resume);
    }

    @Override
    protected boolean isOverflowLimit() {
        return !(size < Integer.MAX_VALUE);
    }

    @Override
    protected void doSave(Resume r) {
        storage.add(r);
        size++;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        int index = (Integer) key;
        storage.remove(index);
        size--;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size]);
    }

    @Override
    public int size() {
        return size;
    }
}
