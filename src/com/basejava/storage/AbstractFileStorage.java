package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File file : list
            ) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles(), "directory is null").length;
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
        if (file.canWrite()) {
            try {
                doWrite(resume, file);
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        } else {
            throw new StorageException("File not be writable", file.getName());
        }
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;
}
