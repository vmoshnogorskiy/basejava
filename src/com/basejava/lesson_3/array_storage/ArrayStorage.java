package com.basejava.lesson_3.array_storage;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (saveVerification(r) == 0) {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    protected int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
