package com.basejava.storage.serialize;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.io.*;

public class FileStorageStrategy implements SerializeStrategy {
    @Override
    public void doWrite(Resume r, OutputStream OutStream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(OutStream)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream inStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", e);
        }
    }
}
