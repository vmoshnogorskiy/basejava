package com.basejava.storage;

import com.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, Integer key) {
        storage[size] = r;
        size++;
    }

    @Override
    protected void doDelete(Integer key) {
        storage[key] = storage[size - 1];
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