package com.basejava.lesson_2.array_storage;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    protected static final int STORAGE_LIMIT = 10000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (STORAGE_LIMIT <= size) {
            System.out.println("ERROR: Резюме не может быть добавлено. Хранилище переполнено");
        } else if (findResume(r.uuid) != -1) {
            System.out.println("ERROR: Резюме с указанным UUID уже содержится в хранилище");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = findResume(r.uuid);
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Указанное Резюме не существует в хранилище");
        }
    }

    public Resume get(String uuid) {
        int index = findResume(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("ERROR: Резюме с указанным uuid не существует в хранилище");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findResume(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }
}
