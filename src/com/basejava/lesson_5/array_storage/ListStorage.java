package com.basejava.lesson_5.array_storage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected List<Resume> storage = new ArrayList<>();
    protected int size = 0;

    @Override
    protected Object findKey(String uuid) {
        return null;
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {

    }

    @Override
    protected boolean isOverflowLimit() {
        return false;
    }

    @Override
    protected void doSave(Resume r) {

    }

    @Override
    protected Resume doGet(Object key) {
        return null;
    }

    @Override
    protected void doDelete(Object key) {

    }

    @Override
    protected boolean isExist(Object key) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return size;
    }
}
