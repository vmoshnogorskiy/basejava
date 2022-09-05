package com.basejava.storage;

import com.basejava.storage.serialize.FileStorageStrategy;

import java.io.File;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    protected ObjectStreamFileStorage(File directory) {
        super(directory, new FileStorageStrategy());
    }
}
