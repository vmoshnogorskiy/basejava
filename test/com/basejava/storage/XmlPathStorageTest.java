package com.basejava.storage;

import com.basejava.storage.serialize.XmlStreamStrategy;

import java.io.IOException;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() throws IOException {
        super(new PathStorage(STORAGE_DIR.getCanonicalPath(), new XmlStreamStrategy()));
    }
}