package com.basejava.lesson_2.array_storage;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
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

    protected abstract int findResume(String uuid);

    protected int saveVerification(Resume r) {
        if (STORAGE_LIMIT <= size) {
            System.out.println("ERROR: Резюме не может быть добавлено. Хранилище переполнено");
            return -1;
        } else if (findResume(r.getUuid()) >= 0) {
            System.out.println("ERROR: Резюме с указанным UUID уже содержится в хранилище");
            return -1;
        }
        return 0;
    }
}
