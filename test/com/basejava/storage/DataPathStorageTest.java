package com.basejava.storage;

import com.basejava.storage.serialize.DataStreamStrategy;

import java.io.IOException;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() throws IOException {
        super(new PathStorage(STORAGE_DIR.getCanonicalPath(), new DataStreamStrategy()));
    }
}