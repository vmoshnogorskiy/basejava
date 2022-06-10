package com.basejava.lesson_3.array_storage;

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
            System.out.println("ERROR: Указанное Резюме не существует в хранилище");
        }
    }

    public void save(Resume r) {
        if (STORAGE_LIMIT <= size) {
            System.out.println("ERROR: Резюме не может быть добавлено. Хранилище переполнено");
        } else if (findResume(r.getUuid()) >= 0) {
            System.out.println("ERROR: Резюме с указанным UUID уже содержится в хранилище");
        } else {
            doSave(r);
        }
    }

    public Resume get(String uuid) {
        int index = findResume(uuid);

        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("ERROR: Резюме с указанным uuid не существует в хранилище");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index >= 0) {
            doDelete(index);
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