package com.basejava.lesson_3.array_storage;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSave(Resume r) {
        int indexKey = Arrays.binarySearch(storage, 0, size, r);
        //Индекс, под которым нужно хранить резюме
        indexKey = -(indexKey + 1);

        if (indexKey != size) {
            //Сдвигаем элементы массива вправо от Индексв
            System.arraycopy(storage, indexKey, storage, indexKey + 1, size - indexKey);
        }
        storage[indexKey] = r;
        size++;
    }

    @Override
    protected void doDelete(int index) {
        for (int i = index; i < size && size < STORAGE_LIMIT; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    @Override
    protected int findResume(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}