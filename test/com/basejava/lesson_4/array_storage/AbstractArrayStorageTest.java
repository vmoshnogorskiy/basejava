package com.basejava.lesson_4.array_storage;

import com.basejava.lesson_4.exception.NotExistStorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume(UUID_4);
        storage.save(r);
        Assert.assertEquals(r, storage.get("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() throws Exception {
        Resume r = new Resume(UUID_5);
        storage.save(r) cas
        Assert.assertEquals(r, storage.get(UUID_5));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] arrayResume = storage.getAll();
        Assert.assertEquals(arrayResume[0], storage.get(UUID_1));
        Assert.assertEquals(arrayResume[1], storage.get(UUID_2));
        Assert.assertEquals(arrayResume[2], storage.get(UUID_3));
        Assert.assertEquals(3, arrayResume.length);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}