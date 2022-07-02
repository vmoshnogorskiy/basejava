package com.basejava.lesson_5.array_storage;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSave(Resume r) {
        storage[size] = r;
        size++;
    }

    @Override
    protected void doDelete(Object key) {
        storage[(Integer) key] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}