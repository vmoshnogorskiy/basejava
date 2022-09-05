package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import com.basejava.storage.serialize.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;
    protected SerializeStrategy serializeStrategy;

    protected AbstractPathStorage(String dir, SerializeStrategy serializeStrategy) {
        Path directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or is not writable");
        }
        this.directory = directory;
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException(directory + " Path can't read", e);
        }
    }

    @Override
    protected Path findKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error " + path, path.getFileName().toString(), e);
        }

    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path, path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Can't delete path " + path, path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Unable to read path " + path, path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> list = new ArrayList<>();
        List<Path> paths;
        try {
            paths = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Can't read path " + directory, e);
        }
        for (Path path : paths) {
            list.add(doGet(path));
        }
        return list;
    }

    protected void doWrite(Resume r, OutputStream path) throws IOException {
        serializeStrategy.doWrite(r, path);
    }

    protected Resume doRead(InputStream path) throws IOException {
        return serializeStrategy.doRead(path);
    }
}