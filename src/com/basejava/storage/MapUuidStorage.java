package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage<String> {

    protected final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected String findKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume resume, String key) {
        storage.put(key, resume);
    }

    @Override
    protected void doSave(Resume r, String key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected boolean isExist(String key) {
        return !(key == null);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
