package com.basejava.storage;

import com.basejava.Config;
import com.basejava.exception.ExistStorageException;
import com.basejava.exception.NotExistStorageException;
import com.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SqlStorageTest {

    protected final Storage storage;
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME_1 = ResumeTestData.getTestResume(UUID_1, "Иванов Иван Иванович",
            "+79090992115", "ivanovii@mail.com", "Ivanov_skype", "Ivanov_profile_github",
            "www.ivanov_homepage.com");
    private static final Resume RESUME_2 = ResumeTestData.getTestResume(UUID_2, "Петров Петр Петрович",
            "+79000017765", "petrovpp@mail.com", "Petrov_skype", "Petrov_profile_github",
            "www.petrov_homepage.com");
    private static final Resume RESUME_3 = ResumeTestData.getTestResume(UUID_3, "Сидоров Сидор Сидорович",
            "+79770229988", "sidorovss@mail.com", "Sidor_skype", "Sidor_github",
            "www.sidor_homepage.com");
    private static final Resume RESUME_4 = ResumeTestData.getTestResume(UUID_4, "Михайлов Михаил Михайлович");

    public SqlStorageTest() {
        storage = Config.getInstance().getSqlStorage();
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Assert.assertEquals(storage.getAllSorted(), new ArrayList<Resume>());
    }

    @Test
    public void update() throws Exception {
        Resume expected = ResumeTestData.getTestResume(UUID_2, "Dummy");
        storage.update(expected);
        Assert.assertEquals(expected, storage.get(expected.getUuid()));
        assertGet(expected);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(ResumeTestData.getTestResume(UUID_NOT_EXIST, "Dummy"));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(expected, storage.getAllSorted());
        assertSize(3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}
