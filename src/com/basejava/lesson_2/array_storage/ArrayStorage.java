package com.basejava.lesson_2.array_storage;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        boolean isError = false;

        if (storage.length <= size) {
            System.out.println("ERROR: Резюме не может быть добавлено. Хранилище переполнено");
            isError = true;
        } else if (findResume(r.uuid) != -1) {
            System.out.println("ERROR: Резюме с указанным UUID уже содержится в хранилище");
            isError = true;
        }
        if (!isError) {
            storage[size] = r;
            size++;
        }
    }

    void update(Resume r) {
        int index = findResume(r.uuid);
        if (index != -1) {
            storage[index] = r;
        }
    }

    Resume get(String uuid) {
        int index = findResume(uuid);
        return index != -1 ? storage[index] : null;
    }

    void delete(String uuid) {
        int index = findResume(uuid);
        if (index != -1) {
            for (int j = index; j < size - 1; j++) {
                storage[j] = storage[j + 1];
            }
            storage[size - 1] = null;
            size--;
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
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
