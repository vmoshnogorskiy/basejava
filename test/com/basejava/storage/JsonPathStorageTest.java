package com.basejava.storage;

import com.basejava.storage.serialize.JsonStreamStrategy;

import java.io.IOException;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() throws IOException {
        super(new PathStorage(STORAGE_DIR.getCanonicalPath(), new JsonStreamStrategy()));
    }
}