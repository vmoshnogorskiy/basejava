package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.serialize.SerializeStrategy;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    protected SerializeStrategy serializeStrategy;

    protected FileStorage(File directory, SerializeStrategy serializeStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    public void clear() {
        File[] list = getListFiles();
        for (File file : list) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }

    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File not be Deleted", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Unable to read file", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.stream(getListFiles()).map(file -> doGet(file)).collect(Collectors.toList());
    }

    protected void doWrite(Resume r, OutputStream file) throws IOException {
        serializeStrategy.doWrite(r, file);
    }

    protected Resume doRead(InputStream file) throws IOException {
        return serializeStrategy.doRead(file);
    }

    private File[] getListFiles() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Unable to read directory contents");
        }
        return list;
    }
}