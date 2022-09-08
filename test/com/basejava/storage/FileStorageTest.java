package com.basejava.storage;

import com.basejava.storage.serialize.FileStorageStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new FileStorageStrategy()));
    }
}