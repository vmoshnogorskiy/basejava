package com.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapResumeStorageTest.class,
                MapUuidStorageTest.class,
                FileStorageTest.class,
                PathStorageTest.class,
                XmlPathStorageTest.class,
                JsonPathStorageTest.class,
                DataPathStorageTest.class
        }
)
public class AllStorageTest {
}