package com.basejava.storage;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Comparator<Resume> COMPARATOR_RESUME = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public void update(Resume r) {
        SK key = findExistingKey(r.getUuid());
        doUpdate(r, key);
    }

    @Override
    public void save(Resume r) {
        SK key = findNotExistingKey(r.getUuid());
        doSave(r, key);
    }

    @Override
    public Resume get(String uuid) {
        SK key = findExistingKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        SK key = findExistingKey(uuid);
        doDelete(key);
    }

    protected SK findExistingKey(String uuid) {
        SK key = findKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected SK findNotExistingKey(String uuid) {
        SK key = findKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(COMPARATOR_RESUME);
        return list;
    }

    protected abstract SK findKey(String uuid);

    protected abstract void doUpdate(Resume resume, SK key);

    protected abstract void doSave(Resume r, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract boolean isExist(SK key);

    protected abstract List<Resume> doGetAll();
}
