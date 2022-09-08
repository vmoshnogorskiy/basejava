package com.basejava.storage;

import com.basejava.storage.serialize.FileStorageStrategy;

import java.io.IOException;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() throws IOException {
        super(new PathStorage(STORAGE_DIR.getCanonicalPath(), new FileStorageStrategy()));
    }
}