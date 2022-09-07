package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.serialize.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Directory is already empty");
        }
        for (File file : list) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Unable to read directory contents");
        }
        return list.length;
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
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(directory + " is empty");
        }
        for (File file : files) {
            if (!file.isDirectory()) {
                list.add(doGet(file));
            }
        }
        return list;
    }

    protected void doWrite(Resume r, OutputStream file) throws IOException {
        serializeStrategy.doWrite(r, file);
    }

    protected Resume doRead(InputStream file) throws IOException {
        return serializeStrategy.doRead(file);
    }
}