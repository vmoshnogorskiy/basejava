package com.basejava.lesson_4.array_storage;

import com.basejava.lesson_4.exception.ExistStorageException;
import com.basejava.lesson_4.exception.NotExistStorageException;
import com.basejava.lesson_4.exception.StorageException;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = findResume(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public void save(Resume r) {
        if (STORAGE_LIMIT <= size) {
            throw new StorageException("ERROR: Резюме не может быть добавлено. Хранилище переполнено", r.getUuid());
        } else if (findResume(r.getUuid()) >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r);
        }
    }

    public Resume get(String uuid) {
        int index = findResume(uuid);

        if (index >= 0) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index >= 0) {
            doDelete(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int findResume(String uuid);

    protected abstract void doSave(Resume r);

    protected abstract void doDelete(int index);
}