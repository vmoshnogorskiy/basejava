package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage {

    protected HashMap<String, Resume> storage = new HashMap<String, Resume>();

    @Override
    protected Object findKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void doSave(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected boolean isExist(Object key) {
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
    public List<Resume> getAllSorted() {
        List<Resume> listStorage = (List<Resume>) storage.values();
        Collections.sort(listStorage);
        return listStorage;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
