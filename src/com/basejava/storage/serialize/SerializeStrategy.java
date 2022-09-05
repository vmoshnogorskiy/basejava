package com.basejava.storage.serialize;

import com.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    void doWrite(Resume r, OutputStream OutStream) throws IOException;

    Resume doRead(InputStream inStream) throws IOException;
}
