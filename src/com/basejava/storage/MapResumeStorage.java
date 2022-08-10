package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume findKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
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
