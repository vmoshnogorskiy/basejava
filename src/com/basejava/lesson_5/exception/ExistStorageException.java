package com.basejava.lesson_5.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("EERROR: Резюме с указанным UUID уже содержится в хранилище", uuid);
    }
}