package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage.set(key, resume);
    }

    @Override
    protected void doSave(Resume r, Integer key) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
