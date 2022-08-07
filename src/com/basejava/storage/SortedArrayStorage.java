package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doSave(Resume r, Object indexKey) {
        //Индекс, под которым нужно хранить резюме
        indexKey = -(((Integer) indexKey) + 1);

        if ((Integer) indexKey != size) {
            //Сдвигаем элементы массива вправо от Индексв
            System.arraycopy(storage, (Integer) indexKey, storage, ((Integer) indexKey) + 1, size - ((Integer) indexKey));
        }
        storage[(Integer) indexKey] = r;
        size++;
    }

    @Override
    protected void doDelete(Object key) {
        for (int i = (Integer) key; i < size && size < STORAGE_LIMIT; i++) {
            storage[i] = storage[i + 1];
        }
        size--;
    }

    @Override
    protected Integer findKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Technical");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}