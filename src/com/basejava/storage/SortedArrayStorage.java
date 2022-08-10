package com.basejava.storage;

import com.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, Integer indexKey) {
        //Индекс, под которым нужно хранить резюме
        indexKey = -(indexKey + 1);
        System.arraycopy(storage, indexKey, storage, indexKey + 1, size - (indexKey));
        storage[indexKey] = r;
        size++;
    }

    @Override
    protected void doDelete(Integer key) {
        int index = key;
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        size--;
    }

    @Override
    protected Integer findKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Technical");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}