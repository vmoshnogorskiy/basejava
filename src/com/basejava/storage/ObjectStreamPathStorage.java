package com.basejava.storage;

import com.basejava.storage.serialize.FileStorageStrategy;

public class ObjectStreamPathStorage extends PathStorage {
    protected ObjectStreamPathStorage(String dir) {
        super(dir, new FileStorageStrategy());
    }
}
